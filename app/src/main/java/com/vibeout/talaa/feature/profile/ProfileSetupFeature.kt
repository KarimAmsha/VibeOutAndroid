@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.vibeout.talaa.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibeout.talaa.R
import com.vibeout.talaa.core.model.PrivacyLevel
import com.vibeout.talaa.core.model.SocialPreference
import com.vibeout.talaa.core.network.dto.UpdatePreferencesRequest
import com.vibeout.talaa.data.AppRepository
import com.vibeout.talaa.ui.components.ChoiceChips
import com.vibeout.talaa.ui.components.VibeOutTopBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileSetupUiState(val loading: Boolean = false, val done: Boolean = false, val error: String? = null)

@HiltViewModel
class ProfileSetupViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    private val _state = MutableStateFlow(ProfileSetupUiState())
    val state: StateFlow<ProfileSetupUiState> = _state.asStateFlow()
    fun save(privacy: PrivacyLevel, social: SocialPreference, notifications: Boolean, location: Boolean, newPeople: Boolean) = viewModelScope.launch {
        _state.value = ProfileSetupUiState(loading = true)
        runCatching {
            repository.updatePreferences(
                UpdatePreferencesRequest(
                    privacyLevel = privacy.name,
                    socialPreference = social.name,
                    allowNotifications = notifications,
                    allowLocationBasedSuggestions = location,
                    allowNewPeople = newPeople,
                )
            )
        }.onSuccess { _state.value = ProfileSetupUiState(done = true) }
            .onFailure { _state.value = ProfileSetupUiState(error = it.message) }
    }
}

@Composable
fun ProfileSetupScreen(onDone: () -> Unit, viewModel: ProfileSetupViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    var privacy by remember { mutableStateOf(PrivacyLevel.MEDIUM) }
    var social by remember { mutableStateOf(SocialPreference.DEPENDS_ON_MOOD) }
    var notifications by remember { mutableStateOf(true) }
    var location by remember { mutableStateOf(true) }
    var newPeople by remember { mutableStateOf(true) }
    LaunchedEffect(state.done) { if (state.done) onDone() }
    Scaffold(topBar = { VibeOutTopBar(stringResource(R.string.profile_setup_title)) }) { padding ->
        Column(Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(stringResource(R.string.profile_setup_body), style = MaterialTheme.typography.bodyLarge)
            Text(stringResource(R.string.privacy), style = MaterialTheme.typography.titleMedium)
            ChoiceChips(
                values = listOf(
                    PrivacyLevel.LOW.name to stringResource(R.string.privacy_low),
                    PrivacyLevel.MEDIUM.name to stringResource(R.string.privacy_medium),
                    PrivacyLevel.HIGH.name to stringResource(R.string.privacy_high),
                ),
                selected = privacy.name,
                onSelected = { privacy = PrivacyLevel.valueOf(it) },
            )
            Text(stringResource(R.string.social_preference), style = MaterialTheme.typography.titleMedium)
            ChoiceChips(
                values = listOf(
                    SocialPreference.SOLO.name to stringResource(R.string.social_solo),
                    SocialPreference.FRIENDS.name to stringResource(R.string.social_friends),
                    SocialPreference.NEW_PEOPLE.name to stringResource(R.string.social_new_people),
                    SocialPreference.DEPENDS_ON_MOOD.name to stringResource(R.string.social_depends),
                ),
                selected = social.name,
                onSelected = { social = SocialPreference.valueOf(it) },
            )
            SettingSwitch(stringResource(R.string.allow_notifications), notifications) { notifications = it }
            SettingSwitch(stringResource(R.string.allow_location_suggestions), location) { location = it }
            SettingSwitch(stringResource(R.string.allow_new_people), newPeople) { newPeople = it }
            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Button(
                onClick = { viewModel.save(privacy, social, notifications, location, newPeople) },
                enabled = !state.loading,
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (state.loading) CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                else Text(stringResource(R.string.continue_label))
            }
        }
    }
}

@Composable private fun SettingSwitch(label: String, checked: Boolean, onChecked: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onChecked)
    }
}
