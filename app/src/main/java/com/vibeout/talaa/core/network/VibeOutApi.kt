package com.vibeout.talaa.core.network

import com.vibeout.talaa.core.model.*
import com.vibeout.talaa.core.network.dto.*
import retrofit2.Response
import retrofit2.http.*

interface VibeOutApi {
    @GET("cities") suspend fun getCities(): Response<ApiEnvelope<List<City>>>
    @GET("cities/{id}") suspend fun getCity(@Path("id") id: String): Response<ApiEnvelope<City>>

    @POST("auth/register") suspend fun register(@Body body: RegisterRequest): Response<ApiEnvelope<AuthDataDto>>
    @POST("auth/login") suspend fun login(@Body body: LoginRequest): Response<ApiEnvelope<AuthDataDto>>
    @POST("auth/refresh") suspend fun refresh(@Body body: RefreshRequest): Response<ApiEnvelope<AuthDataDto>>
    @POST("auth/logout") suspend fun logout(): Response<ApiEnvelope<Unit>>
    @GET("auth/me") suspend fun authMe(): Response<ApiEnvelope<User>>

    @GET("users/me") suspend fun getMe(): Response<ApiEnvelope<User>>
    @PUT("users/me") suspend fun updateMe(@Body body: UpdateUserRequest): Response<ApiEnvelope<User>>
    @GET("users/{id}/public") suspend fun getPublicUser(@Path("id") id: String): Response<ApiEnvelope<PublicUser>>
    @DELETE("users/me") suspend fun deleteAccount(): Response<ApiEnvelope<Unit>>
    @GET("users/preferences") suspend fun getPreferences(): Response<ApiEnvelope<UserPreference>>
    @PUT("users/preferences") suspend fun updatePreferences(@Body body: UpdatePreferencesRequest): Response<ApiEnvelope<UserPreference>>

    @GET("places") suspend fun getPlaces(
        @Query("cityId") cityId: String? = null,
        @Query("mood") mood: String? = null,
        @Query("placeType") placeType: String? = null,
        @Query("priceLevel") priceLevel: String? = null,
        @Query("search") search: String? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 50,
    ): Response<ApiEnvelope<List<Place>>>

    @GET("places/recommended") suspend fun getRecommendedPlaces(
        @Query("cityId") cityId: String,
        @Query("mood") mood: String? = null,
        @Query("budgetMin") budgetMin: Int? = null,
        @Query("budgetMax") budgetMax: Int? = null,
        @Query("distanceKm") distanceKm: Double? = null,
        @Query("latitude") latitude: Double? = null,
        @Query("longitude") longitude: Double? = null,
    ): Response<ApiEnvelope<List<Place>>>

    @GET("places/{id}") suspend fun getPlace(@Path("id") id: String): Response<ApiEnvelope<Place>>
    @POST("places/{id}/save") suspend fun savePlace(@Path("id") id: String): Response<ApiEnvelope<Unit>>
    @DELETE("places/{id}/save") suspend fun unsavePlace(@Path("id") id: String): Response<ApiEnvelope<Unit>>
    @GET("users/me/saved-places") suspend fun getSavedPlaces(): Response<ApiEnvelope<List<Place>>>

    @POST("ai/plans/generate") suspend fun generatePlan(@Body body: GeneratePlanRequest): Response<ApiEnvelope<AiPlanResult>>
    @GET("ai/plans/history") suspend fun getPlanHistory(): Response<ApiEnvelope<List<AiPlanResult>>>
    @GET("ai/plans/{id}") suspend fun getPlan(@Path("id") id: String): Response<ApiEnvelope<AiPlanResult>>
    @POST("ai/plans/{id}/select") suspend fun selectPlan(@Path("id") id: String, @Body body: SelectPlanRequest): Response<ApiEnvelope<AiPlanResult>>

    @GET("vibes") suspend fun getVibes(
        @Query("cityId") cityId: String? = null,
        @Query("mood") mood: String? = null,
        @Query("status") status: String? = null,
        @Query("language") language: String? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 50,
    ): Response<ApiEnvelope<List<Vibe>>>

    @POST("vibes") suspend fun createVibe(@Body body: CreateVibeRequest): Response<ApiEnvelope<Vibe>>
    @GET("vibes/{id}") suspend fun getVibe(@Path("id") id: String): Response<ApiEnvelope<Vibe>>
    @POST("vibes/{id}/join") suspend fun joinVibe(@Path("id") id: String, @Body body: JoinVibeRequest): Response<ApiEnvelope<VibeParticipant>>
    @POST("vibes/{id}/leave") suspend fun leaveVibe(@Path("id") id: String): Response<ApiEnvelope<Unit>>
    @POST("vibes/{id}/approve") suspend fun approveParticipant(@Path("id") id: String, @Body body: UserIdRequest): Response<ApiEnvelope<VibeParticipant>>
    @POST("vibes/{id}/reject") suspend fun rejectParticipant(@Path("id") id: String, @Body body: RejectParticipantRequest): Response<ApiEnvelope<VibeParticipant>>
    @POST("vibes/{id}/cancel") suspend fun cancelVibe(@Path("id") id: String, @Body body: CancelVibeRequest): Response<ApiEnvelope<Vibe>>

    @GET("chats/{vibeId}/messages") suspend fun getMessages(
        @Path("vibeId") vibeId: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 100,
    ): Response<ApiEnvelope<List<ChatMessage>>>
    @POST("chats/{vibeId}/messages") suspend fun sendMessage(@Path("vibeId") vibeId: String, @Body body: SendMessageRequest): Response<ApiEnvelope<ChatMessage>>

    @POST("reports") suspend fun createReport(@Body body: CreateReportRequest): Response<ApiEnvelope<Unit>>
    @GET("reports/my") suspend fun getMyReports(): Response<ApiEnvelope<List<Map<String, Any>>>>

    @POST("blocks") suspend fun blockUser(@Body body: CreateBlockRequest): Response<ApiEnvelope<Unit>>
    @DELETE("blocks/{blockedUserId}") suspend fun unblockUser(@Path("blockedUserId") blockedUserId: String): Response<ApiEnvelope<Unit>>
    @GET("blocks") suspend fun getBlocks(): Response<ApiEnvelope<List<PublicUser>>>

    @GET("notifications") suspend fun getNotifications(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 100,
    ): Response<ApiEnvelope<List<NotificationItem>>>
    @PUT("notifications/{id}/read") suspend fun markNotificationRead(@Path("id") id: String): Response<ApiEnvelope<Unit>>
    @PUT("notifications/read-all") suspend fun markAllNotificationsRead(): Response<ApiEnvelope<Unit>>

    @POST("auth/forgot-password")
    suspend fun forgotPassword(
        @Body body: ForgotPasswordRequest,
    ): Response<ApiEnvelope<PasswordResetMessage>>

    @POST("auth/verify-reset-code")
    suspend fun verifyResetCode(
        @Body body: VerifyResetCodeRequest,
    ): Response<ApiEnvelope<VerifyResetCodeResponse>>

    @POST("auth/reset-password")
    suspend fun resetPassword(
        @Body body: ResetPasswordRequest,
    ): Response<ApiEnvelope<PasswordResetMessage>>
}
