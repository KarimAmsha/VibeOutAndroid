package com.vibeout.talaa.core.network

import com.google.gson.Gson
import com.vibeout.talaa.BuildConfig
import com.vibeout.talaa.core.network.dto.ApiEnvelope
import com.vibeout.talaa.core.network.dto.AuthDataDto
import com.vibeout.talaa.core.network.dto.RefreshRequest
import com.vibeout.talaa.core.storage.TokenStore
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(private val tokenStore: TokenStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenStore.accessTokenBlocking()
        val request = chain.request().newBuilder().apply {
            if (!token.isNullOrBlank() && chain.request().header("Authorization") == null) {
                header("Authorization", "Bearer $token")
            }
        }.build()
        return chain.proceed(request)
    }
}

@Singleton
class TokenAuthenticator @Inject constructor(
    private val tokenStore: TokenStore,
    private val gson: Gson,
) : Authenticator {
    private val refreshClient by lazy { OkHttpClient.Builder().build() }

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) return null
        val refresh = runBlocking { tokenStore.refreshTokenNow() } ?: return null
        val json = gson.toJson(RefreshRequest(refresh))
        val request = Request.Builder()
            .url(BuildConfig.API_BASE_URL + "auth/refresh")
            .post(json.toRequestBody("application/json".toMediaType()))
            .build()
        return runCatching {
            refreshClient.newCall(request).execute().use { refreshResponse ->
                if (!refreshResponse.isSuccessful) {
                    runBlocking { tokenStore.clear() }
                    return null
                }
                val body = refreshResponse.body?.string() ?: return null
                val type = com.google.gson.reflect.TypeToken.getParameterized(
                    ApiEnvelope::class.java,
                    AuthDataDto::class.java,
                ).type
                val envelope: ApiEnvelope<AuthDataDto> = gson.fromJson(body, type)
                val data = envelope.data ?: return null
                runBlocking { tokenStore.save(data.accessToken, data.refreshToken, data.user.id) }
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${data.accessToken}")
                    .build()
            }
        }.getOrNull()
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}
