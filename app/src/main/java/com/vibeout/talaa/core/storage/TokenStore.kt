package com.vibeout.talaa.core.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenStore @Inject constructor(@ApplicationContext context: Context) {
    private object Keys {
        const val accessToken = "access_token"
        const val refreshToken = "refresh_token"
        const val userId = "user_id"
    }

    private val prefs = EncryptedSharedPreferences.create(
        "secure_session",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    private val _accessToken = MutableStateFlow(prefs.getString(Keys.accessToken, null))
    private val _userId = MutableStateFlow(prefs.getString(Keys.userId, null))
    val accessToken: StateFlow<String?> = _accessToken
    val userId: StateFlow<String?> = _userId

    suspend fun save(accessToken: String, refreshToken: String, userId: String) {
        prefs.edit()
            .putString(Keys.accessToken, accessToken)
            .putString(Keys.refreshToken, refreshToken)
            .putString(Keys.userId, userId)
            .apply()
        _accessToken.value = accessToken
        _userId.value = userId
    }

    suspend fun clear() {
        prefs.edit().clear().apply()
        _accessToken.value = null
        _userId.value = null
    }

    suspend fun accessTokenNow(): String? = _accessToken.value
    suspend fun refreshTokenNow(): String? = prefs.getString(Keys.refreshToken, null)
    fun accessTokenBlocking(): String? = _accessToken.value
}
