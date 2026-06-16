package com.vibeout.talaa.data

import com.vibeout.talaa.core.database.CityDao
import com.vibeout.talaa.core.database.CityEntity
import com.vibeout.talaa.core.database.SavedPlaceDao
import com.vibeout.talaa.core.database.SavedPlaceEntity
import com.vibeout.talaa.core.model.*
import com.vibeout.talaa.core.network.ApiExecutor
import com.vibeout.talaa.core.network.VibeOutApi
import com.vibeout.talaa.core.network.dto.*
import com.vibeout.talaa.core.storage.TokenStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val api: VibeOutApi,
    private val executor: ApiExecutor,
    private val tokenStore: TokenStore,
    private val cityDao: CityDao,
    private val savedPlaceDao: SavedPlaceDao,
) {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    suspend fun login(email: String, password: String): User {
        val auth = executor.execute { api.login(LoginRequest(email.trim(), password)) }
        tokenStore.save(auth.accessToken, auth.refreshToken, auth.user.id)
        _currentUser.value = auth.user
        return auth.user
    }

    suspend fun register(request: RegisterRequest): User {
        val auth = executor.execute { api.register(request) }
        tokenStore.save(auth.accessToken, auth.refreshToken, auth.user.id)
        _currentUser.value = auth.user
        return auth.user
    }

    suspend fun restoreSession(): User? {
        if (tokenStore.accessTokenNow().isNullOrBlank()) return null
        return runCatching {
            executor.execute { api.authMe() }.also { _currentUser.value = it }
        }.getOrElse {
            if (it is com.vibeout.talaa.core.network.ApiException && it.httpStatus == 401) tokenStore.clear()
            null
        }
    }

    suspend fun logout() {
        runCatching { executor.execute { api.logout() } }
        tokenStore.clear()
        savedPlaceDao.clear()
        _currentUser.value = null
    }


    suspend fun forgotPassword(
        email: String,
        locale: String?,
    ): PasswordResetMessage =
        executor.execute {
            api.forgotPassword(
                ForgotPasswordRequest(
                    email = email.trim().lowercase(),
                    locale = locale,
                )
            )
        }

    suspend fun verifyResetCode(
        email: String,
        code: String,
    ): VerifyResetCodeResponse =
        executor.execute {
            api.verifyResetCode(
                VerifyResetCodeRequest(
                    email = email.trim().lowercase(),
                    code = code,
                )
            )
        }

    suspend fun resetPassword(
        email: String,
        resetToken: String,
        newPassword: String,
    ): PasswordResetMessage =
        executor.execute {
            api.resetPassword(
                ResetPasswordRequest(
                    email = email.trim().lowercase(),
                    resetToken = resetToken,
                    newPassword = newPassword,
                )
            )
        }

    suspend fun getCities(force: Boolean = false): List<City> {
        if (!force) {
            val cached = cityDao.getAll()
            if (cached.isNotEmpty()) return cached.map { it.toModel() }
        }
        return runCatching {
            executor.execute { api.getCities() }.also { list ->
                cityDao.clear()
                cityDao.upsertAll(list.map { it.toEntity() })
            }
        }.getOrElse { error ->
            val cached = cityDao.getAll().map { it.toModel() }
            if (cached.isNotEmpty()) cached else throw error
        }
    }

    suspend fun getMe(): User = executor.execute { api.getMe() }.also { _currentUser.value = it }

    suspend fun updateMe(request: UpdateUserRequest): User =
        executor.execute { api.updateMe(request) }.also { _currentUser.value = it }

    suspend fun updatePreferences(request: UpdatePreferencesRequest): UserPreference =
        executor.execute { api.updatePreferences(request) }

    suspend fun generatePlan(request: GeneratePlanRequest): AiPlanResult =
        executor.execute { api.generatePlan(request) }

    suspend fun getPlan(id: String): AiPlanResult = executor.execute { api.getPlan(id) }
    suspend fun selectPlan(id: String, index: Int): AiPlanResult = executor.execute { api.selectPlan(id, SelectPlanRequest(index)) }

    suspend fun getPlaces(cityId: String?, search: String? = null, mood: String? = null): List<Place> {
        val savedIds = savedPlaceDao.observeIds().first().toSet()
        return executor.execute { api.getPlaces(cityId = cityId, search = search, mood = mood) }
            .map { it.copy(isSaved = it.id in savedIds) }
    }

    suspend fun getPlace(id: String): Place {
        val savedIds = savedPlaceDao.observeIds().first().toSet()
        return executor.execute { api.getPlace(id) }.let { it.copy(isSaved = it.id in savedIds) }
    }

    suspend fun getSavedPlaces(): List<Place> {
        val list = executor.execute { api.getSavedPlaces() }
        list.forEach { savedPlaceDao.save(SavedPlaceEntity(it.id)) }
        return list.map { it.copy(isSaved = true) }
    }

    suspend fun setPlaceSaved(id: String, saved: Boolean) {
        if (saved) {
            executor.execute { api.savePlace(id) }
            savedPlaceDao.save(SavedPlaceEntity(id))
        } else {
            executor.execute { api.unsavePlace(id) }
            savedPlaceDao.remove(id)
        }
    }

    suspend fun getVibes(cityId: String? = null): List<Vibe> = executor.execute { api.getVibes(cityId = cityId) }
    suspend fun getVibe(id: String): Vibe = executor.execute { api.getVibe(id) }
    suspend fun createVibe(request: CreateVibeRequest): Vibe = executor.execute { api.createVibe(request) }
    suspend fun joinVibe(id: String, message: String?): VibeParticipant = executor.execute { api.joinVibe(id, JoinVibeRequest(message)) }
    suspend fun leaveVibe(id: String) { executor.execute<Unit> { api.leaveVibe(id) } }
    suspend fun approveParticipant(vibeId: String, userId: String): VibeParticipant = executor.execute { api.approveParticipant(vibeId, UserIdRequest(userId)) }
    suspend fun rejectParticipant(vibeId: String, userId: String): VibeParticipant = executor.execute { api.rejectParticipant(vibeId, RejectParticipantRequest(userId)) }
    suspend fun cancelVibe(id: String, reason: String?) = executor.execute { api.cancelVibe(id, CancelVibeRequest(reason)) }

    suspend fun getMessages(vibeId: String): List<ChatMessage> = executor.execute { api.getMessages(vibeId) }
    suspend fun sendMessage(vibeId: String, message: String): ChatMessage = executor.execute { api.sendMessage(vibeId, SendMessageRequest(message.trim())) }

    suspend fun getNotifications(): List<NotificationItem> = executor.execute { api.getNotifications() }
    suspend fun markNotificationRead(id: String) { executor.execute<Unit> { api.markNotificationRead(id) } }
    suspend fun markAllNotificationsRead() { executor.execute<Unit> { api.markAllNotificationsRead() } }

    suspend fun report(targetType: String, targetId: String, reason: String, description: String?) {
        executor.execute<Unit> { api.createReport(CreateReportRequest(targetType, targetId, reason, description)) }
    }

    suspend fun blockUser(userId: String, reason: String? = null) {
        executor.execute<Unit> { api.blockUser(CreateBlockRequest(userId, reason)) }
    }

    private fun City.toEntity() = CityEntity(id, nameAr, nameEn, nameTr, countryCode, timezone, latitude, longitude, status)
    private fun CityEntity.toModel() = City(id, nameAr, nameEn, nameTr, countryCode, timezone, latitude, longitude, status)
}
