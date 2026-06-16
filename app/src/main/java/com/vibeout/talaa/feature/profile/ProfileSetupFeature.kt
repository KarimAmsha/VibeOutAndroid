@file:OptIn(
    androidx.compose.material3.ExperimentalMaterial3Api::class,
    androidx.compose.foundation.layout.ExperimentalLayoutApi::class,
)

package com.vibeout.talaa.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.vibeout.talaa.ui.designsystem.*
import com.vibeout.talaa.ui.theme.BrandEnergy
import com.vibeout.talaa.ui.theme.BrandMint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileSetupUiState(
    val loading: Boolean = false,
    val done: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class ProfileSetupViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileSetupUiState())
    val state: StateFlow<ProfileSetupUiState> = _state.asStateFlow()

    fun save(
        privacy: PrivacyLevel,
        social: SocialPreference,
        notifications: Boolean,
        location: Boolean,
        newPeople: Boolean,
    ) = viewModelScope.launch {
        _state.value = ProfileSetupUiState(loading = true)

        runCatching {
            repository.updatePreferences(
                UpdatePreferencesRequest(
                    preferredMoods = emptyList(),
                    preferredPlaceTypes = emptyList(),
                    privacyLevel = privacy.name,
                    socialPreference = social.name,
                    allowNotifications = notifications,
                    allowLocationBasedSuggestions = location,
                    allowNewPeople = newPeople,
                )
            )
        }.onSuccess {
            _state.value = ProfileSetupUiState(done = true)
        }.onFailure {
            _state.value = ProfileSetupUiState(error = it.message)
        }
    }
}

@Composable
fun ProfileSetupScreen(
    onDone: () -> Unit,
    viewModel: ProfileSetupViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    var privacy by remember { mutableStateOf(PrivacyLevel.MEDIUM) }
    var social by remember { mutableStateOf(SocialPreference.DEPENDS_ON_MOOD) }
    var notifications by remember { mutableStateOf(true) }
    var location by remember { mutableStateOf(true) }
    var newPeople by remember { mutableStateOf(true) }

    LaunchedEffect(state.done) {
        if (state.done) onDone()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            VibeTopBar(stringResource(R.string.profile_setup_title))
        },
    ) { padding ->
        VibeScreen(Modifier.padding(padding)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 28.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    PremiumAccentCard(
                        title = stringResource(R.string.profile_setup_title),
                        body = stringResource(R.string.profile_setup_body),
                        icon = Icons.Default.Tune,
                    )
                }

                item {
                    PremiumSectionTitle(stringResource(R.string.privacy))
                    ChoiceChips(
                        values = listOf(
                            PrivacyLevel.LOW.name to stringResource(R.string.privacy_low),
                            PrivacyLevel.MEDIUM.name to stringResource(R.string.privacy_medium),
                            PrivacyLevel.HIGH.name to stringResource(R.string.privacy_high),
                        ),
                        selected = privacy.name,
                        onSelected = { privacy = PrivacyLevel.valueOf(it) },
                    )
                }

                item {
                    PremiumSectionTitle(stringResource(R.string.social_preference))
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
                }

                item {
                    PreferenceSwitch(
                        icon = Icons.Default.NotificationsActive,
                        title = stringResource(R.string.allow_notifications),
                        checked = notifications,
                        tint = BrandEnergy,
                        onChecked = { notifications = it },
                    )
                }

                item {
                    PreferenceSwitch(
                        icon = Icons.Default.NearMe,
                        title = stringResource(R.string.allow_location_suggestions),
                        checked = location,
                        tint = BrandMint,
                        onChecked = { location = it },
                    )
                }

                item {
                    PreferenceSwitch(
                        icon = Icons.Default.Groups,
                        title = stringResource(R.string.allow_new_people),
                        checked = newPeople,
                        onChecked = { newPeople = it },
                    )
                }

                state.error?.let { raw ->
                    item {
                        val visibleMessage =
                            if (raw.contains("internal server error", ignoreCase = true)) {
                                stringResource(R.string.error_generic)
                            } else {
                                raw
                            }

                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(14.dp),
                            ) {
                                Icon(Icons.Default.ErrorOutline, contentDescription = null)
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    visibleMessage,
                                    modifier = Modifier.weight(1f),
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                        }
                    }
                }

                item {
                    VibePrimaryButton(
                        text = stringResource(R.string.continue_label),
                        onClick = {
                            viewModel.save(
                                privacy = privacy,
                                social = social,
                                notifications = notifications,
                                location = location,
                                newPeople = newPeople,
                            )
                        },
                        loading = state.loading,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
private fun PreferenceSwitch(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    checked: Boolean,
    tint: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary,
    onChecked: (Boolean) -> Unit,
) {
    PremiumInfoRow(
        icon = icon,
        title = title,
        tint = tint,
        onClick = { onChecked(!checked) },
        trailing = {
            Switch(
                checked = checked,
                onCheckedChange = onChecked,
            )
        },
    )
}
