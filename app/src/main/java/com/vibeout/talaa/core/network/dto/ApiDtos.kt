package com.vibeout.talaa.core.network.dto

import com.google.gson.JsonElement
import com.vibeout.talaa.core.model.*

data class ApiEnvelope<T>(
    val success: Boolean = false,
    val message: String = "",
    val data: T? = null,
    val pagination: PaginationDto? = null,
    val error: ApiErrorDto? = null,
)

data class ApiErrorDto(
    val code: String? = null,
    val details: JsonElement? = null,
)

data class PaginationDto(
    val page: Int = 1,
    val limit: Int = 20,
    val totalItems: Int = 0,
    val totalPages: Int = 0,
    val hasNextPage: Boolean = false,
    val hasPreviousPage: Boolean = false,
)

data class LoginRequest(val email: String, val password: String)

data class RegisterRequest(
    val firstName: String,
    val displayName: String?,
    val email: String,
    val phone: String,
    val password: String,
    val birthYear: Int?,
    val cityId: String,
    val languages: List<String>,
    val interests: List<String>,
)

data class RefreshRequest(val refreshToken: String)

data class AuthDataDto(
    val user: User,
    val accessToken: String,
    val refreshToken: String,
)

data class UpdateUserRequest(
    val firstName: String? = null,
    val displayName: String? = null,
    val birthYear: Int? = null,
    val cityId: String? = null,
    val bio: String? = null,
    val profilePhotoUrl: String? = null,
    val languages: List<String>? = null,
    val interests: List<String>? = null,
)

data class UpdatePreferencesRequest(
    val preferredBudgetMin: Int? = null,
    val preferredBudgetMax: Int? = null,
    val preferredDistanceKm: Double? = null,
    val preferredMoods: List<String>? = null,
    val preferredPlaceTypes: List<String>? = null,
    val socialPreference: String? = null,
    val noiseLevel: String? = null,
    val privacyLevel: String? = null,
    val allowNewPeople: Boolean? = null,
    val allowNotifications: Boolean? = null,
    val allowLocationBasedSuggestions: Boolean? = null,
)

data class GeneratePlanRequest(
    val cityId: String,
    val mood: String,
    val budgetMin: Int?,
    val budgetMax: Int?,
    val durationMinutes: Int,
    val preferredDistanceKm: Double?,
    val wantsNewPeople: Boolean,
    val prompt: String?,
)

data class SelectPlanRequest(val selectedPlanIndex: Int)

data class CreateVibeRequest(
    val title: String,
    val description: String?,
    val mood: String,
    val placeId: String?,
    val cityId: String,
    val meetingArea: String?,
    val startTime: String,
    val endTime: String,
    val maxPeople: Int,
    val language: String,
    val visibility: String,
    val approvalRequired: Boolean,
)

data class JoinVibeRequest(val joinMessage: String?)
data class UserIdRequest(val userId: String)
data class RejectParticipantRequest(val userId: String, val reason: String? = null)
data class CancelVibeRequest(val reason: String? = null)
data class SendMessageRequest(val message: String)

data class CreateReportRequest(
    val targetType: String,
    val targetId: String,
    val reason: String,
    val description: String?,
)

data class CreateBlockRequest(val blockedUserId: String, val reason: String? = null)
