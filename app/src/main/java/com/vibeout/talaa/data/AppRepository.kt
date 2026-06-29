package com.vibeout.talaa.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.vibeout.talaa.core.database.CityDao
import com.vibeout.talaa.core.database.CityEntity
import com.vibeout.talaa.core.database.SavedPlaceDao
import com.vibeout.talaa.core.database.SavedPlaceEntity
import com.vibeout.talaa.core.model.*
import com.vibeout.talaa.core.network.dto.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single source of truth for app data, backed entirely by Cloud Firestore and
 * Firebase Authentication. Public method signatures are kept transport-agnostic
 * so the UI/ViewModel layer is unaffected by the backend implementation.
 *
 * Firestore data model:
 *   cities/{cityId}
 *   places/{placeId}
 *   users/{uid}                         (profile + embedded preferences + city)
 *   users/{uid}/savedPlaces/{placeId}
 *   users/{uid}/notifications/{id}
 *   users/{uid}/blocks/{blockedUid}
 *   users/{uid}/aiPlans/{id}
 *   vibes/{vibeId}
 *   vibes/{vibeId}/participants/{uid}
 *   vibes/{vibeId}/messages/{id}
 *   reports/{id}
 */
@Singleton
class AppRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    @Suppress("unused") private val storage: FirebaseStorage,
    private val cityDao: CityDao,
    private val savedPlaceDao: SavedPlaceDao,
) {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val usersCol get() = firestore.collection("users")
    private val citiesCol get() = firestore.collection("cities")
    private val placesCol get() = firestore.collection("places")
    private val vibesCol get() = firestore.collection("vibes")
    private val reportsCol get() = firestore.collection("reports")

    private fun requireUid(): String =
        auth.currentUser?.uid ?: throw IllegalStateException("Not authenticated")

    private fun nowIso(): String = Instant.now().toString()

    // region Auth ----------------------------------------------------------

    suspend fun login(email: String, password: String): User {
        auth.signInWithEmailAndPassword(email.trim(), password).await()
        return getMe()
    }

    suspend fun register(request: RegisterRequest): User {
        val result = auth.createUserWithEmailAndPassword(request.email.trim(), request.password).await()
        val uid = result.user?.uid ?: throw IllegalStateException("Registration failed")
        val city = runCatching { fetchCity(request.cityId) }.getOrNull()
        val user = User(
            id = uid,
            firstName = request.firstName,
            displayName = request.displayName?.takeIf { it.isNotBlank() } ?: request.firstName,
            email = request.email.trim(),
            phone = request.phone,
            birthYear = request.birthYear,
            role = "USER",
            cityId = request.cityId,
            city = city,
            languages = request.languages,
            interests = request.interests,
            preferences = UserPreference(),
        )
        usersCol.document(uid).set(user).await()
        _currentUser.value = user
        return user
    }

    suspend fun restoreSession(): User? {
        if (auth.currentUser == null) return null
        return runCatching { getMe() }.getOrNull()
    }

    suspend fun logout() {
        auth.signOut()
        savedPlaceDao.clear()
        _currentUser.value = null
    }

    suspend fun forgotPassword(email: String, locale: String?): PasswordResetMessage {
        auth.sendPasswordResetEmail(email.trim()).await()
        return PasswordResetMessage("A password reset link was sent to your email.")
    }

    // Firebase resets passwords through the link emailed to the user, so the
    // in-app code/token steps are not used. Kept for signature compatibility.
    suspend fun verifyResetCode(email: String, code: String): VerifyResetCodeResponse =
        throw UnsupportedOperationException("Password is reset via the email link.")

    suspend fun resetPassword(email: String, resetToken: String, newPassword: String): PasswordResetMessage =
        throw UnsupportedOperationException("Password is reset via the email link.")

    // endregion

    // region User ----------------------------------------------------------

    suspend fun getMe(): User {
        val uid = requireUid()
        val snap = usersCol.document(uid).get().await()
        val user = snap.toObject(User::class.java)?.copy(id = uid)
            ?: throw IllegalStateException("Profile not found")
        _currentUser.value = user
        return user
    }

    suspend fun updateMe(request: UpdateUserRequest): User {
        val uid = requireUid()
        val updates = buildMap<String, Any> {
            request.firstName?.let { put("firstName", it) }
            request.displayName?.let { put("displayName", it) }
            request.birthYear?.let { put("birthYear", it) }
            request.bio?.let { put("bio", it) }
            request.profilePhotoUrl?.let { put("profilePhotoUrl", it) }
            request.languages?.let { put("languages", it) }
            request.interests?.let { put("interests", it) }
            request.cityId?.let { cityId ->
                put("cityId", cityId)
                fetchCity(cityId)?.let { put("city", it) }
            }
        }
        if (updates.isNotEmpty()) usersCol.document(uid).update(updates).await()
        return getMe()
    }

    suspend fun updatePreferences(request: UpdatePreferencesRequest): UserPreference {
        val uid = requireUid()
        val current = _currentUser.value?.preferences
            ?: runCatching { getMe().preferences }.getOrNull()
            ?: UserPreference()
        val updated = current.copy(
            preferredBudgetMin = request.preferredBudgetMin ?: current.preferredBudgetMin,
            preferredBudgetMax = request.preferredBudgetMax ?: current.preferredBudgetMax,
            preferredDistanceKm = request.preferredDistanceKm ?: current.preferredDistanceKm,
            preferredMoods = request.preferredMoods ?: current.preferredMoods,
            preferredPlaceTypes = request.preferredPlaceTypes ?: current.preferredPlaceTypes,
            socialPreference = request.socialPreference ?: current.socialPreference,
            noiseLevel = request.noiseLevel ?: current.noiseLevel,
            privacyLevel = request.privacyLevel ?: current.privacyLevel,
            allowNewPeople = request.allowNewPeople ?: current.allowNewPeople,
            allowNotifications = request.allowNotifications ?: current.allowNotifications,
            allowLocationBasedSuggestions = request.allowLocationBasedSuggestions
                ?: current.allowLocationBasedSuggestions,
        )
        usersCol.document(uid).update("preferences", updated).await()
        _currentUser.value = _currentUser.value?.copy(preferences = updated)
        return updated
    }

    suspend fun getPreferences(): UserPreference = getMe().preferences ?: UserPreference()

    suspend fun deleteAccount() {
        val uid = requireUid()
        usersCol.document(uid).delete().await()
        auth.currentUser?.delete()?.await()
        savedPlaceDao.clear()
        cityDao.clear()
        _currentUser.value = null
    }

    private suspend fun publicUser(userId: String): PublicUser? {
        val user = usersCol.document(userId).get().await().toObject(User::class.java) ?: return null
        return PublicUser(
            id = userId,
            firstName = user.firstName,
            displayName = user.displayName,
            profilePhotoUrl = user.profilePhotoUrl,
            bio = user.bio,
            city = user.city,
            languages = user.languages,
            interests = user.interests,
            trustScore = user.trustScore,
            isPhoneVerified = user.isPhoneVerified,
            status = user.status,
        )
    }

    // endregion

    // region Cities --------------------------------------------------------

    suspend fun getCities(force: Boolean = false): List<City> {
        if (!force) {
            val cached = cityDao.getAll()
            if (cached.isNotEmpty()) return cached.map { it.toModel() }
        }
        return runCatching {
            val snap = citiesCol.get().await()
            val cities = snap.documents
                .mapNotNull { it.toObject(City::class.java)?.copy(id = it.id) }
                .filter { it.status != "INACTIVE" }
            cityDao.clear()
            cityDao.upsertAll(cities.map { it.toEntity() })
            cities
        }.getOrElse { error ->
            val cached = cityDao.getAll().map { it.toModel() }
            if (cached.isNotEmpty()) cached else throw error
        }
    }

    private suspend fun fetchCity(id: String): City? =
        citiesCol.document(id).get().await().toObject(City::class.java)?.copy(id = id)

    // endregion

    // region Places --------------------------------------------------------

    suspend fun getPlaces(cityId: String?, search: String? = null, mood: String? = null): List<Place> {
        val savedIds = savedPlaceDao.observeIds().first().toSet()
        var query: Query = placesCol
        if (cityId != null) query = query.whereEqualTo("cityId", cityId)
        val snap = query.get().await()
        var places = snap.documents.mapNotNull { it.toObject(Place::class.java)?.copy(id = it.id) }
        if (!mood.isNullOrBlank()) {
            val wanted = moodToTags(mood)
            places = places.filter { place ->
                place.moodTags.map { it.uppercase() }.any { it in wanted }
            }.ifEmpty { places }
        }
        if (!search.isNullOrBlank()) {
            val needle = search.trim().lowercase()
            places = places.filter {
                it.name.lowercase().contains(needle) ||
                    (it.area?.lowercase()?.contains(needle) == true)
            }
        }
        return places
            .sortedByDescending { it.ratingInternal }
            .map { it.copy(isSaved = it.id in savedIds) }
    }

    suspend fun getPlace(id: String): Place {
        val savedIds = savedPlaceDao.observeIds().first().toSet()
        val place = placesCol.document(id).get().await().toObject(Place::class.java)?.copy(id = id)
            ?: throw IllegalStateException("Place not found")
        return place.copy(isSaved = place.id in savedIds)
    }

    suspend fun getSavedPlaces(): List<Place> {
        val uid = requireUid()
        val savedSnap = usersCol.document(uid).collection("savedPlaces").get().await()
        val ids = savedSnap.documents.map { it.id }
        savedPlaceDao.clear()
        ids.forEach { savedPlaceDao.save(SavedPlaceEntity(it)) }
        return ids.mapNotNull { placeId ->
            runCatching {
                placesCol.document(placeId).get().await()
                    .toObject(Place::class.java)?.copy(id = placeId, isSaved = true)
            }.getOrNull()
        }
    }

    suspend fun setPlaceSaved(id: String, saved: Boolean) {
        val uid = requireUid()
        val ref = usersCol.document(uid).collection("savedPlaces").document(id)
        if (saved) {
            ref.set(mapOf("placeId" to id, "createdAt" to nowIso())).await()
            savedPlaceDao.save(SavedPlaceEntity(id))
        } else {
            ref.delete().await()
            savedPlaceDao.remove(id)
        }
    }

    // endregion

    // region Vibes ---------------------------------------------------------

    suspend fun getVibes(cityId: String? = null): List<Vibe> {
        var query: Query = vibesCol.whereEqualTo("status", VibeStatus.OPEN.name)
        if (cityId != null) query = query.whereEqualTo("cityId", cityId)
        val snap = query.get().await()
        return snap.documents
            .mapNotNull { mapVibe(it, withParticipants = false) }
            .sortedBy { it.startTime }
    }

    suspend fun getVibe(id: String): Vibe =
        mapVibe(vibesCol.document(id).get().await(), withParticipants = true)
            ?: throw IllegalStateException("Vibe not found")

    suspend fun createVibe(request: CreateVibeRequest): Vibe {
        val uid = requireUid()
        val me = _currentUser.value ?: getMe()
        val placeName = request.placeId?.let {
            runCatching { placesCol.document(it).get().await().getString("name") }.getOrNull()
        }
        val ref = vibesCol.document()
        val data = mapOf(
            "creatorId" to uid,
            "creatorName" to me.displayName,
            "title" to request.title,
            "description" to request.description,
            "mood" to request.mood,
            "placeId" to request.placeId,
            "placeName" to placeName,
            "cityId" to request.cityId,
            "meetingArea" to request.meetingArea,
            "startTime" to request.startTime,
            "endTime" to request.endTime,
            "maxPeople" to request.maxPeople,
            "language" to request.language,
            "visibility" to request.visibility,
            "approvalRequired" to request.approvalRequired,
            "status" to VibeStatus.OPEN.name,
            "approvedParticipantsCount" to 1,
            "createdAt" to nowIso(),
        )
        ref.set(data).await()
        ref.collection("participants").document(uid).set(
            mapOf(
                "userId" to uid,
                "userName" to me.displayName,
                "role" to "CREATOR",
                "status" to ParticipantStatus.APPROVED.name,
                "createdAt" to nowIso(),
            ),
        ).await()
        return getVibe(ref.id)
    }

    suspend fun joinVibe(id: String, message: String?): VibeParticipant {
        val uid = requireUid()
        val me = _currentUser.value ?: getMe()
        val approvalRequired = vibesCol.document(id).get().await().getBoolean("approvalRequired") ?: true
        val status = if (approvalRequired) ParticipantStatus.PENDING.name else ParticipantStatus.APPROVED.name
        vibesCol.document(id).collection("participants").document(uid).set(
            mapOf(
                "userId" to uid,
                "userName" to me.displayName,
                "role" to "PARTICIPANT",
                "status" to status,
                "joinMessage" to message,
                "createdAt" to nowIso(),
            ),
        ).await()
        if (status == ParticipantStatus.APPROVED.name) {
            vibesCol.document(id).update("approvedParticipantsCount", FieldValue.increment(1)).await()
        }
        return VibeParticipant(
            id = uid, userId = uid, role = "PARTICIPANT", status = status,
            joinMessage = message, user = PublicUser(id = uid, displayName = me.displayName, firstName = me.firstName),
        )
    }

    suspend fun leaveVibe(id: String) {
        val uid = requireUid()
        val ref = vibesCol.document(id).collection("participants").document(uid)
        val wasApproved = ref.get().await().getString("status") == ParticipantStatus.APPROVED.name
        ref.delete().await()
        if (wasApproved) {
            vibesCol.document(id).update("approvedParticipantsCount", FieldValue.increment(-1)).await()
        }
    }

    suspend fun approveParticipant(vibeId: String, userId: String): VibeParticipant {
        val ref = vibesCol.document(vibeId).collection("participants").document(userId)
        ref.update("status", ParticipantStatus.APPROVED.name).await()
        vibesCol.document(vibeId).update("approvedParticipantsCount", FieldValue.increment(1)).await()
        val p = ref.get().await()
        return participantOf(p, userId, ParticipantStatus.APPROVED.name)
    }

    suspend fun rejectParticipant(vibeId: String, userId: String): VibeParticipant {
        val ref = vibesCol.document(vibeId).collection("participants").document(userId)
        ref.update("status", ParticipantStatus.REJECTED.name).await()
        val p = ref.get().await()
        return participantOf(p, userId, ParticipantStatus.REJECTED.name)
    }

    suspend fun cancelVibe(id: String, reason: String?) {
        vibesCol.document(id).update("status", VibeStatus.CANCELLED.name).await()
    }

    private fun participantOf(doc: DocumentSnapshot, userId: String, status: String) = VibeParticipant(
        id = userId,
        userId = userId,
        role = doc.getString("role") ?: "PARTICIPANT",
        status = status,
        joinMessage = doc.getString("joinMessage"),
        user = doc.getString("userName")?.let { PublicUser(id = userId, displayName = it, firstName = it) },
    )

    private suspend fun mapVibe(doc: DocumentSnapshot, withParticipants: Boolean): Vibe? {
        if (!doc.exists()) return null
        val creatorId = doc.getString("creatorId") ?: ""
        val creatorName = doc.getString("creatorName")
        val placeId = doc.getString("placeId")
        val placeName = doc.getString("placeName")
        val base = Vibe(
            id = doc.id,
            creatorId = creatorId,
            title = doc.getString("title") ?: "",
            description = doc.getString("description"),
            mood = doc.getString("mood") ?: "",
            placeId = placeId,
            cityId = doc.getString("cityId") ?: "",
            meetingArea = doc.getString("meetingArea"),
            startTime = doc.getString("startTime") ?: "",
            endTime = doc.getString("endTime") ?: "",
            maxPeople = (doc.getLong("maxPeople") ?: 0L).toInt(),
            language = doc.getString("language") ?: "en",
            visibility = doc.getString("visibility") ?: VibeVisibility.PUBLIC.name,
            approvalRequired = doc.getBoolean("approvalRequired") ?: true,
            status = doc.getString("status") ?: VibeStatus.OPEN.name,
            approvedParticipantsCount = (doc.getLong("approvedParticipantsCount") ?: 0L).toInt(),
            creator = creatorName?.let { PublicUser(id = creatorId, displayName = it, firstName = it) },
            place = if (placeId != null && placeName != null) Place(id = placeId, name = placeName) else null,
        )
        if (!withParticipants) return base

        val uid = auth.currentUser?.uid
        val partSnap = vibesCol.document(doc.id).collection("participants").get().await()
        val participants = partSnap.documents.map { p ->
            val pUserId = p.getString("userId") ?: p.id
            VibeParticipant(
                id = p.id,
                userId = pUserId,
                role = p.getString("role") ?: "PARTICIPANT",
                status = p.getString("status") ?: ParticipantStatus.PENDING.name,
                joinMessage = p.getString("joinMessage"),
                user = p.getString("userName")?.let { PublicUser(id = pUserId, displayName = it, firstName = it) },
            )
        }
        return base.copy(
            participants = participants,
            approvedParticipantsCount = participants.count { it.status == ParticipantStatus.APPROVED.name },
            currentUserParticipationStatus = participants.firstOrNull { it.userId == uid }?.status,
        )
    }

    // endregion

    // region Chat ----------------------------------------------------------

    suspend fun getMessages(vibeId: String): List<ChatMessage> {
        val snap = vibesCol.document(vibeId).collection("messages")
            .orderBy("createdAt", Query.Direction.ASCENDING)
            .limit(200)
            .get().await()
        return snap.documents.map { d ->
            val senderId = d.getString("senderId") ?: ""
            ChatMessage(
                id = d.id,
                senderId = senderId,
                message = d.getString("message") ?: "",
                messageType = d.getString("messageType") ?: "TEXT",
                status = d.getString("status") ?: "SENT",
                createdAt = d.getString("createdAt") ?: "",
                sender = d.getString("senderName")?.let { PublicUser(id = senderId, displayName = it, firstName = it) },
            )
        }
    }

    suspend fun sendMessage(vibeId: String, message: String): ChatMessage {
        val uid = requireUid()
        val me = _currentUser.value ?: getMe()
        val created = nowIso()
        val ref = vibesCol.document(vibeId).collection("messages").document()
        ref.set(
            mapOf(
                "senderId" to uid,
                "senderName" to me.displayName,
                "message" to message.trim(),
                "messageType" to "TEXT",
                "status" to "SENT",
                "createdAt" to created,
            ),
        ).await()
        return ChatMessage(
            id = ref.id, senderId = uid, message = message.trim(), createdAt = created,
            sender = PublicUser(id = uid, displayName = me.displayName, firstName = me.firstName),
        )
    }

    // endregion

    // region Notifications -------------------------------------------------

    @Suppress("UNCHECKED_CAST")
    suspend fun getNotifications(): List<NotificationItem> {
        val uid = requireUid()
        val snap = usersCol.document(uid).collection("notifications")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(100)
            .get().await()
        return snap.documents.map { d ->
            NotificationItem(
                id = d.id,
                title = d.getString("title") ?: "",
                body = d.getString("body") ?: "",
                type = d.getString("type") ?: "",
                dataJson = d.get("dataJson") as? Map<String, Any>,
                readAt = d.getString("readAt"),
                createdAt = d.getString("createdAt") ?: "",
            )
        }
    }

    suspend fun markNotificationRead(id: String) {
        val uid = requireUid()
        usersCol.document(uid).collection("notifications").document(id)
            .update("readAt", nowIso()).await()
    }

    suspend fun markAllNotificationsRead() {
        val uid = requireUid()
        val snap = usersCol.document(uid).collection("notifications")
            .whereEqualTo("readAt", null).get().await()
        if (snap.isEmpty) return
        val batch = firestore.batch()
        val stamp = nowIso()
        snap.documents.forEach { batch.update(it.reference, "readAt", stamp) }
        batch.commit().await()
    }

    // endregion

    // region Safety --------------------------------------------------------

    suspend fun report(targetType: String, targetId: String, reason: String, description: String?) {
        val uid = requireUid()
        reportsCol.add(
            mapOf(
                "reporterId" to uid,
                "targetType" to targetType,
                "targetId" to targetId,
                "reason" to reason,
                "description" to description,
                "status" to "OPEN",
                "createdAt" to nowIso(),
            ),
        ).await()
    }

    suspend fun blockUser(userId: String, reason: String? = null) {
        val uid = requireUid()
        val pub = runCatching { publicUser(userId) }.getOrNull()
        usersCol.document(uid).collection("blocks").document(userId).set(
            mapOf(
                "blockedUserId" to userId,
                "reason" to reason,
                "displayName" to (pub?.displayName ?: userId),
                "firstName" to (pub?.firstName ?: ""),
                "profilePhotoUrl" to pub?.profilePhotoUrl,
                "createdAt" to nowIso(),
            ),
        ).await()
    }

    suspend fun getBlocks(): List<PublicUser> {
        val uid = requireUid()
        val snap = usersCol.document(uid).collection("blocks").get().await()
        return snap.documents.map { d ->
            PublicUser(
                id = d.id,
                firstName = d.getString("firstName") ?: "",
                displayName = d.getString("displayName") ?: d.id,
                profilePhotoUrl = d.getString("profilePhotoUrl"),
            )
        }
    }

    suspend fun unblockUser(userId: String) {
        usersCol.document(requireUid()).collection("blocks").document(userId).delete().await()
    }

    // endregion

    // region AI plans (on-device recommendation) ---------------------------

    suspend fun generatePlan(request: GeneratePlanRequest): AiPlanResult {
        val uid = requireUid()
        val places = getPlaces(cityId = request.cityId, search = null, mood = request.mood)
        val ranked = places.sortedByDescending { scorePlace(it, request) }
        val plans = buildPlans(ranked, request)
        val ref = usersCol.document(uid).collection("aiPlans").document()
        val result = AiPlanResult(
            id = ref.id,
            status = if (plans.isEmpty()) "EMPTY" else "READY",
            plans = plans,
        )
        ref.set(result).await()
        return result
    }

    suspend fun getPlan(id: String): AiPlanResult {
        val uid = requireUid()
        return usersCol.document(uid).collection("aiPlans").document(id).get().await()
            .toObject(AiPlanResult::class.java)?.copy(id = id)
            ?: throw IllegalStateException("Plan not found")
    }

    suspend fun selectPlan(id: String, index: Int): AiPlanResult {
        val uid = requireUid()
        usersCol.document(uid).collection("aiPlans").document(id)
            .update("selectedPlanIndex", index).await()
        return getPlan(id)
    }

    private fun scorePlace(place: Place, request: GeneratePlanRequest): Double {
        val wanted = moodToTags(request.mood)
        val moodHit = if (place.moodTags.map { it.uppercase() }.any { it in wanted }) 1.0 else 0.3
        val budgetHit = budgetMatch(place.priceLevel, request.budgetMin, request.budgetMax)
        val quality = (place.ratingInternal / 5.0).coerceIn(0.0, 1.0)
        return 0.30 * moodHit + 0.15 * budgetHit + 0.10 * quality + 0.20 * 0.6 + 0.15 * 0.6 + 0.05 * 0.5
    }

    private fun buildPlans(ranked: List<Place>, request: GeneratePlanRequest): List<AiPlanOption> {
        if (ranked.isEmpty()) return emptyList()
        val plans = mutableListOf<AiPlanOption>()
        plans += planFrom(listOfNotNull(ranked.getOrNull(0)), request)
        if (ranked.size >= 2) plans += planFrom(listOfNotNull(ranked.getOrNull(0), ranked.getOrNull(1)), request)
        if (ranked.size >= 3) plans += planFrom(listOfNotNull(ranked.getOrNull(2)), request)
        return plans.take(3)
    }

    private fun planFrom(places: List<Place>, request: GeneratePlanRequest): AiPlanOption {
        val times = listOf("19:00", "20:15", "21:30")
        val items = places.mapIndexed { index, place ->
            PlanItem(
                time = times.getOrElse(index) { null },
                type = "PLACE",
                placeId = place.id,
                title = place.name,
                reason = place.area ?: place.moodTags.firstOrNull(),
            )
        }
        val costs = places.map { priceRange(it.priceLevel) }
        val minCost = costs.sumOf { it.first }
        val maxCost = costs.sumOf { it.second }
        return AiPlanOption(
            title = places.joinToString(" + ") { it.name },
            summary = places.mapNotNull { it.area }.distinct().joinToString(" • ").ifBlank { null },
            estimatedCostMin = minCost,
            estimatedCostMax = maxCost,
            durationMinutes = request.durationMinutes,
            suitableFor = if (request.wantsNewPeople) listOf("NEW_PEOPLE") else listOf("SOLO"),
            items = items,
        )
    }

    private fun priceRange(priceLevel: String): Pair<Int, Int> = when (priceLevel) {
        PriceLevel.LOW.name -> 50 to 150
        PriceLevel.MEDIUM.name -> 150 to 350
        PriceLevel.HIGH.name -> 350 to 700
        PriceLevel.PREMIUM.name -> 700 to 1500
        else -> 100 to 300
    }

    private fun budgetMatch(priceLevel: String, budgetMin: Int?, budgetMax: Int?): Double {
        if (budgetMax == null) return 0.7
        val (min, max) = priceRange(priceLevel)
        val mid = (min + max) / 2
        return if (mid in (budgetMin ?: 0)..budgetMax) 1.0 else 0.3
    }

    private fun moodToTags(mood: String): Set<String> = when (runCatching { MoodType.valueOf(mood) }.getOrNull()) {
        MoodType.QUIET_COFFEE -> setOf("QUIET", "CAFE", "CALM")
        MoodType.STUDY_WORK -> setOf("STUDY", "QUIET")
        MoodType.MEET_NEW_PEOPLE, MoodType.GROUP_VIBE -> setOf("SOCIAL")
        MoodType.WALK -> setOf("WALK")
        MoodType.FOOD -> setOf("FOOD")
        MoodType.BUDGET_ACTIVITY -> setOf("BUDGET")
        MoodType.PHOTOGRAPHY -> setOf("PHOTOGRAPHY")
        MoodType.NEW_EXPERIENCE, MoodType.BORED, MoodType.GO_OUT, MoodType.TODAY_EVENTS ->
            setOf("NEW_EXPERIENCE", "SOCIAL")
        null -> emptySet()
    }

    // endregion

    private fun City.toEntity() = CityEntity(id, nameAr, nameEn, nameTr, countryCode, timezone, latitude, longitude, status)
    private fun CityEntity.toModel() = City(id, nameAr, nameEn, nameTr, countryCode, timezone, latitude, longitude, status)
}
