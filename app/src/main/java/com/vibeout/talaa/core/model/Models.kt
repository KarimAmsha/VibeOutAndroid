package com.vibeout.talaa.core.model

enum class MoodType {
    BORED, GO_OUT, QUIET_COFFEE, STUDY_WORK, MEET_NEW_PEOPLE, WALK, FOOD,
    BUDGET_ACTIVITY, PHOTOGRAPHY, GROUP_VIBE, NEW_EXPERIENCE, TODAY_EVENTS
}

enum class PlaceType {
    CAFE, RESTAURANT, STUDY_PLACE, WALKING_AREA, DESSERT, ACTIVITY,
    PHOTOGRAPHY_SPOT, QUIET_PLACE, PARK, CULTURAL_PLACE, OTHER
}

enum class PriceLevel { LOW, MEDIUM, HIGH, PREMIUM }
enum class PrivacyLevel { LOW, MEDIUM, HIGH }
enum class SocialPreference { SOLO, FRIENDS, NEW_PEOPLE, DEPENDS_ON_MOOD }
enum class VibeVisibility { PUBLIC, PRIVATE_INVITE }
enum class VibeStatus { DRAFT, OPEN, FULL, IN_PROGRESS, COMPLETED, CANCELLED, REPORTED, REMOVED_BY_ADMIN }
enum class ParticipantStatus { PENDING, APPROVED, REJECTED, LEFT, REMOVED, BLOCKED }
enum class ReportTargetType { USER, VIBE, PLACE, MESSAGE }
enum class ReportReason { HARASSMENT, FAKE_PROFILE, INAPPROPRIATE_BEHAVIOR, SPAM, UNSAFE_PLACE, HATE_OR_ABUSE, OTHER }
enum class ThemeMode { SYSTEM, LIGHT, DARK }

data class City(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val nameTr: String,
    val countryCode: String = "TR",
    val timezone: String = "Europe/Istanbul",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val status: String = "ACTIVE",
)

data class UserPreference(
    val preferredBudgetMin: Int? = null,
    val preferredBudgetMax: Int? = null,
    val preferredDistanceKm: Double? = null,
    val preferredMoods: List<String> = emptyList(),
    val preferredPlaceTypes: List<String> = emptyList(),
    val socialPreference: String = SocialPreference.DEPENDS_ON_MOOD.name,
    val noiseLevel: String? = null,
    val privacyLevel: String = PrivacyLevel.MEDIUM.name,
    val allowNewPeople: Boolean = true,
    val allowNotifications: Boolean = true,
    val allowLocationBasedSuggestions: Boolean = true,
)

data class User(
    val id: String,
    val firstName: String,
    val displayName: String,
    val email: String,
    val phone: String,
    val birthYear: Int? = null,
    val role: String = "USER",
    val city: City? = null,
    val languages: List<String> = emptyList(),
    val interests: List<String> = emptyList(),
    val profilePhotoUrl: String? = null,
    val bio: String? = null,
    val trustScore: Int = 70,
    val isPhoneVerified: Boolean = false,
    val isEmailVerified: Boolean = false,
    val status: String = "ACTIVE",
    val preferences: UserPreference? = null,
)

data class PlacePhoto(
    val id: String = "",
    val url: String,
    val thumbnailUrl: String? = null,
    val altText: String? = null,
    val sortOrder: Int = 0,
)

data class Place(
    val id: String,
    val name: String,
    val description: String? = null,
    val cityId: String = "",
    val city: City? = null,
    val area: String? = null,
    val address: String? = null,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val type: String = PlaceType.OTHER.name,
    val priceLevel: String = PriceLevel.MEDIUM.name,
    val moodTags: List<String> = emptyList(),
    val amenities: Map<String, Any>? = null,
    val openingHours: Map<String, Any>? = null,
    val ratingInternal: Double = 0.0,
    val safetyNote: String? = null,
    val isPartner: Boolean = false,
    val photos: List<PlacePhoto> = emptyList(),
    val isSaved: Boolean = false,
)

data class PlanItem(
    val time: String? = null,
    val type: String = "PLACE",
    val placeId: String? = null,
    val title: String,
    val reason: String? = null,
)

data class AiPlanOption(
    val title: String,
    val summary: String? = null,
    val estimatedCostMin: Int? = null,
    val estimatedCostMax: Int? = null,
    val durationMinutes: Int? = null,
    val suitableFor: List<String> = emptyList(),
    val items: List<PlanItem> = emptyList(),
    val warnings: List<String> = emptyList(),
)

data class AiPlanResult(
    val id: String,
    val status: String,
    val plans: List<AiPlanOption>,
)

data class PublicUser(
    val id: String,
    val firstName: String,
    val displayName: String,
    val profilePhotoUrl: String? = null,
    val bio: String? = null,
    val city: City? = null,
    val languages: List<String> = emptyList(),
    val interests: List<String> = emptyList(),
    val trustScore: Int = 70,
    val isPhoneVerified: Boolean = false,
    val status: String = "ACTIVE",
)

data class VibeParticipant(
    val id: String,
    val userId: String,
    val role: String,
    val status: String,
    val joinMessage: String? = null,
    val user: PublicUser? = null,
)

data class Vibe(
    val id: String,
    val creatorId: String,
    val title: String,
    val description: String? = null,
    val mood: String,
    val placeId: String? = null,
    val cityId: String,
    val meetingArea: String? = null,
    val startTime: String,
    val endTime: String,
    val maxPeople: Int,
    val language: String,
    val visibility: String = VibeVisibility.PUBLIC.name,
    val approvalRequired: Boolean = true,
    val status: String = VibeStatus.OPEN.name,
    val safetyLevel: String? = null,
    val creator: PublicUser? = null,
    val place: Place? = null,
    val approvedParticipantsCount: Int = 0,
    val currentUserParticipationStatus: String? = null,
    val participants: List<VibeParticipant> = emptyList(),
)

data class ChatMessage(
    val id: String,
    val chatId: String? = null,
    val senderId: String,
    val message: String,
    val messageType: String = "TEXT",
    val status: String = "SENT",
    val createdAt: String,
    val sender: PublicUser? = null,
)

data class NotificationItem(
    val id: String,
    val title: String,
    val body: String,
    val type: String,
    val dataJson: Map<String, Any>? = null,
    val readAt: String? = null,
    val createdAt: String,
)
