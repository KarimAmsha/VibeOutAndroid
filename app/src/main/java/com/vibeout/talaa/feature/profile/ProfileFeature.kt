@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.vibeout.talaa.feature.profile

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.vibeout.talaa.R
import com.vibeout.talaa.core.model.ThemeMode
import com.vibeout.talaa.core.model.User
import com.vibeout.talaa.core.network.dto.UpdateUserRequest
import com.vibeout.talaa.core.storage.AppPreferences
import com.vibeout.talaa.data.AppRepository
import com.vibeout.talaa.ui.common.UiState
import com.vibeout.talaa.ui.common.localizedName
import com.vibeout.talaa.ui.components.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AppRepository,
    private val appPreferences: AppPreferences,
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<User>>(repository.currentUser.value?.let { UiState.Success(it) } ?: UiState.Loading)
    val state: StateFlow<UiState<User>> = _state.asStateFlow()
    val theme = appPreferences.themeMode
    init { load() }
    fun load() = viewModelScope.launch {
        runCatching { repository.getMe() }
            .onSuccess { _state.value = UiState.Success(it) }
            .onFailure { _state.value = UiState.Error(it.message ?: "") }
    }
    fun update(firstName: String, displayName: String, bio: String) = viewModelScope.launch {
        runCatching { repository.updateMe(UpdateUserRequest(firstName = firstName, displayName = displayName, bio = bio)) }
            .onSuccess { _state.value = UiState.Success(it) }
            .onFailure { _state.value = UiState.Error(it.message ?: "") }
    }
    fun setTheme(mode: ThemeMode) = viewModelScope.launch { appPreferences.setTheme(mode) }
    fun logout(onDone: () -> Unit) = viewModelScope.launch { repository.logout(); onDone() }
}

@Composable
fun ProfileScreen(onSettings: () -> Unit, onSafety: () -> Unit, viewModel: ProfileViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.profile)) }, actions = { IconButton(onClick = onSettings) { Icon(Icons.Default.Settings, stringResource(R.string.settings)) } }) }) { padding ->
        when (val content = state) {
            UiState.Loading, UiState.Idle -> LoadingPane(Modifier.padding(padding))
            is UiState.Error -> ErrorPane(content.message, viewModel::load, Modifier.padding(padding))
            is UiState.Success -> {
                val user = content.data
                var firstName by remember(user.id, user.firstName) { mutableStateOf(user.firstName) }
                var displayName by remember(user.id, user.displayName) { mutableStateOf(user.displayName) }
                var bio by remember(user.id, user.bio) { mutableStateOf(user.bio.orEmpty()) }
                Column(Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(model = user.profilePhotoUrl, contentDescription = null, modifier = Modifier.size(96.dp))
                    Spacer(Modifier.height(12.dp))
                    Text(user.displayName, style = MaterialTheme.typography.headlineSmall)
                    Text(user.city?.localizedName(Locale.getDefault()).orEmpty())
                    Spacer(Modifier.height(20.dp))
                    OutlinedTextField(firstName, { firstName = it }, Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.first_name)) })
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(displayName, { displayName = it }, Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.display_name)) })
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(bio, { bio = it }, Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.bio)) }, minLines = 3)
                    Spacer(Modifier.height(14.dp))
                    Button(onClick = { viewModel.update(firstName, displayName, bio) }, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.update)) }
                    OutlinedButton(onClick = onSafety, modifier = Modifier.fillMaxWidth()) { Icon(Icons.Default.HealthAndSafety, null); Spacer(Modifier.width(8.dp)); Text(stringResource(R.string.safety_center)) }
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(onBack: () -> Unit, onLoggedOut: () -> Unit, viewModel: ProfileViewModel = hiltViewModel()) {
    val theme by viewModel.theme.collectAsState(initial = ThemeMode.SYSTEM)
    var showLogout by remember { mutableStateOf(false) }
    Scaffold(topBar = { VibeOutTopBar(stringResource(R.string.settings), onBack) }) { padding ->
        Column(Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState())) {
            SectionTitle(stringResource(R.string.app_language))
            listOf("ar" to R.string.arabic, "en" to R.string.english, "tr" to R.string.turkish).forEach { (tag, label) ->
                ListItem(
                    headlineContent = { Text(stringResource(label)) },
                    leadingContent = { Icon(Icons.Default.Language, null) },
                    modifier = Modifier.fillMaxWidth(),
                    trailingContent = { RadioButton(selected = AppCompatDelegate.getApplicationLocales().toLanguageTags().startsWith(tag), onClick = { AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag)) }) },
                )
            }
            SectionTitle(stringResource(R.string.theme))
            ThemeMode.entries.forEach { mode ->
                val label = when (mode) { ThemeMode.SYSTEM -> R.string.theme_system; ThemeMode.LIGHT -> R.string.theme_light; ThemeMode.DARK -> R.string.theme_dark }
                ListItem(
                    headlineContent = { Text(stringResource(label)) },
                    leadingContent = { Icon(if (mode == ThemeMode.DARK) Icons.Default.DarkMode else Icons.Default.LightMode, null) },
                    trailingContent = { RadioButton(selected = theme == mode, onClick = { viewModel.setTheme(mode) }) },
                )
            }
            HorizontalDivider()
            ListItem(
                headlineContent = { Text(stringResource(R.string.logout), color = MaterialTheme.colorScheme.error) },
                leadingContent = { Icon(Icons.Default.Logout, null, tint = MaterialTheme.colorScheme.error) },
                modifier = Modifier.fillMaxWidth(),
                trailingContent = { IconButton(onClick = { showLogout = true }) { Icon(Icons.Default.ChevronRight, null) } },
            )
        }
    }
    if (showLogout) ConfirmDialog(stringResource(R.string.logout), stringResource(R.string.logout_confirm), onConfirm = { showLogout = false; viewModel.logout(onLoggedOut) }, onDismiss = { showLogout = false })
}
