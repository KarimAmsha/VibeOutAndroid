package com.vibeout.talaa.core.storage

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.vibeout.talaa.core.model.ThemeMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.appDataStore by preferencesDataStore(name = "app_preferences")

@Singleton
class AppPreferences @Inject constructor(@ApplicationContext private val context: Context) {
    private object Keys {
        val onboardingDone = booleanPreferencesKey("onboarding_done")
        val theme = stringPreferencesKey("theme")
        val lastMood = stringPreferencesKey("last_mood")
    }

    val onboardingDone: Flow<Boolean> = context.appDataStore.data.map { it[Keys.onboardingDone] ?: false }
    val themeMode: Flow<ThemeMode> = context.appDataStore.data.map {
        runCatching { ThemeMode.valueOf(it[Keys.theme] ?: ThemeMode.SYSTEM.name) }.getOrDefault(ThemeMode.SYSTEM)
    }

    suspend fun setOnboardingDone(value: Boolean = true) = context.appDataStore.edit { it[Keys.onboardingDone] = value }
    suspend fun setTheme(mode: ThemeMode) = context.appDataStore.edit { it[Keys.theme] = mode.name }
    suspend fun setLastMood(mood: String) = context.appDataStore.edit { it[Keys.lastMood] = mood }
}
