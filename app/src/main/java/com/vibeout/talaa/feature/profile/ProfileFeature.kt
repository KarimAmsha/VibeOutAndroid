@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.vibeout.talaa.feature.profile

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.vibeout.talaa.R
import com.vibeout.talaa.core.model.City
import com.vibeout.talaa.core.model.ThemeMode
import com.vibeout.talaa.core.model.User
import com.vibeout.talaa.core.network.dto.UpdateUserRequest
import com.vibeout.talaa.core.storage.AppPreferences
import com.vibeout.talaa.data.AppRepository
import com.vibeout.talaa.ui.common.UiState
import com.vibeout.talaa.ui.common.localizedName
import com.vibeout.talaa.ui.components.ConfirmDialog
import com.vibeout.talaa.ui.components.SimpleListPicker
import com.vibeout.talaa.ui.designsystem.*
import com.vibeout.talaa.ui.theme.BrandEnergy
import com.vibeout.talaa.ui.theme.BrandMint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AppRepository,
    private val appPreferences: AppPreferences,
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<User>>(
        repository.currentUser.value?.let { UiState.Success(it) } ?: UiState.Loading
    )
    val state: StateFlow<UiState<User>> = _state.asStateFlow()
    val theme = appPreferences.themeMode

    private val _accountAction = MutableStateFlow(AccountActionState())
    val accountAction: StateFlow<AccountActionState> = _accountAction.asStateFlow()

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities.asStateFlow()

    init { load() }

    fun loadCities() = viewModelScope.launch {
        if (_cities.value.isNotEmpty()) return@launch
        runCatching { repository.getCities() }.onSuccess { _cities.value = it }
    }

    fun changeCity(cityId: String) = viewModelScope.launch {
        runCatching { repository.updateMe(UpdateUserRequest(cityId = cityId)) }
            .onSuccess { _state.value = UiState.Success(it) }
            .onFailure { _state.value = UiState.Error(it.message ?: "") }
    }

    fun load() = viewModelScope.launch {
        runCatching { repository.getMe() }
            .onSuccess { _state.value = UiState.Success(it) }
            .onFailure { _state.value = UiState.Error(it.message ?: "") }
    }

    fun update(firstName: String, displayName: String, bio: String) = viewModelScope.launch {
        runCatching {
            repository.updateMe(
                UpdateUserRequest(
                    firstName = firstName,
                    displayName = displayName,
                    bio = bio,
                )
            )
        }.onSuccess {
            _state.value = UiState.Success(it)
        }.onFailure {
            _state.value = UiState.Error(it.message ?: "")
        }
    }

    fun setTheme(mode: ThemeMode) = viewModelScope.launch { appPreferences.setTheme(mode) }

    fun logout(onDone: () -> Unit) = viewModelScope.launch {
        repository.logout()
        onDone()
    }

    fun deleteAccount(onDone: () -> Unit) = viewModelScope.launch {
        _accountAction.value = AccountActionState(deleting = true)
        runCatching { repository.deleteAccount() }
            .onSuccess {
                _accountAction.value = AccountActionState()
                onDone()
            }
            .onFailure {
                _accountAction.value = AccountActionState(error = it.message)
            }
    }
}

data class AccountActionState(
    val deleting: Boolean = false,
    val error: String? = null,
)

@Composable
fun ProfileScreen(
    onSettings: () -> Unit,
    onSafety: () -> Unit,
    onMyVibes: () -> Unit = {},
    onSavedPlaces: () -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val currentLocale = LocalConfiguration.current.locales[0]
    val interestLabels = ProfileInterestCatalog.associate { it.first to stringResource(it.second) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            VibeTopBar(
                title = stringResource(R.string.profile),
                actions = {
                    IconButton(onClick = onSettings) {
                        Icon(Icons.Default.Settings, stringResource(R.string.settings))
                    }
                },
            )
        },
    ) { padding ->
        when (val content = state) {
            UiState.Loading, UiState.Idle -> PremiumLoadingState(Modifier.padding(padding))
            is UiState.Error -> PremiumErrorState(
                content.message,
                stringResource(R.string.retry),
                viewModel::load,
                Modifier.padding(padding),
            )
            is UiState.Success -> {
                val user = content.data
                var firstName by remember(user.id, user.firstName) { mutableStateOf(user.firstName) }
                var displayName by remember(user.id, user.displayName) { mutableStateOf(user.displayName) }
                var bio by remember(user.id, user.bio) { mutableStateOf(user.bio.orEmpty()) }

                VibeScreen(Modifier.padding(padding)) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        ProfileHeader(user)

                        PremiumInfoRow(
                            icon = Icons.Default.LocationCity,
                            title = user.city?.localizedName(currentLocale).orEmpty(),
                            subtitle = user.email,
                            tint = BrandMint,
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            PremiumStatusChip(
                                text = if (user.isEmailVerified) stringResource(R.string.verified)
                                else stringResource(R.string.not_verified),
                                color = if (user.isEmailVerified) BrandMint else BrandEnergy,
                            )
                            PremiumStatusChip("${user.trustScore}%")
                        }

                        PremiumSectionTitle(stringResource(R.string.edit_profile))

                        VibeTextField(
                            firstName,
                            { firstName = it },
                            stringResource(R.string.first_name),
                            Modifier.fillMaxWidth(),
                            Icons.Default.Person,
                        )
                        VibeTextField(
                            displayName,
                            { displayName = it },
                            stringResource(R.string.display_name),
                            Modifier.fillMaxWidth(),
                            Icons.Default.Badge,
                        )
                        VibeTextField(
                            bio,
                            { bio = it },
                            stringResource(R.string.bio),
                            Modifier.fillMaxWidth(),
                            Icons.Default.Edit,
                            singleLine = false,
                        )

                        VibePrimaryButton(
                            text = stringResource(R.string.update),
                            onClick = { viewModel.update(firstName, displayName, bio) },
                            enabled = firstName.length >= 2 && displayName.length >= 2,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        user.interests
                            .mapNotNull { interestLabels[it] }
                            .takeIf { it.isNotEmpty() }
                            ?.let { labels ->
                                PremiumInfoRow(
                                    icon = Icons.Default.Interests,
                                    title = stringResource(R.string.interests),
                                    subtitle = labels.joinToString(" • "),
                                    tint = BrandMint,
                                )
                            }

                        PremiumSectionTitle(stringResource(R.string.app_name))

                        PremiumInfoRow(
                            icon = Icons.Default.Groups,
                            title = stringResource(R.string.my_vibes),
                            tint = BrandEnergy,
                            onClick = onMyVibes,
                            trailing = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
                        )

                        PremiumInfoRow(
                            icon = Icons.Default.Bookmark,
                            title = stringResource(R.string.saved_places),
                            tint = BrandMint,
                            onClick = onSavedPlaces,
                            trailing = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
                        )

                        PremiumInfoRow(
                            icon = Icons.Default.HealthAndSafety,
                            title = stringResource(R.string.safety_center),
                            subtitle = stringResource(R.string.safety_tip_public),
                            tint = BrandEnergy,
                            onClick = onSafety,
                            trailing = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
                        )
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileHeader(user: User) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(82.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    user.displayName.take(1).uppercase(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                )
                if (!user.profilePhotoUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = user.profilePhotoUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                    )
                }
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    user.displayName,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    user.bio.orEmpty(),
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.72f),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onLoggedOut: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val theme by viewModel.theme.collectAsState(initial = ThemeMode.SYSTEM)
    val accountAction by viewModel.accountAction.collectAsState()
    val userState by viewModel.state.collectAsState()
    val cities by viewModel.cities.collectAsState()
    val currentLocale = LocalConfiguration.current.locales[0]
    var showLogout by remember { mutableStateOf(false) }
    var showDelete by remember { mutableStateOf(false) }
    var cityPickerOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { viewModel.loadCities() }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { VibeTopBar(stringResource(R.string.settings), onBack) },
    ) { padding ->
        VibeScreen(Modifier.padding(padding)) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                PremiumSectionTitle(stringResource(R.string.city))
                PremiumInfoRow(
                    icon = Icons.Default.LocationCity,
                    title = (userState as? UiState.Success)?.data?.city?.localizedName(currentLocale)
                        ?: stringResource(R.string.choose_city),
                    subtitle = stringResource(R.string.change_city),
                    tint = BrandMint,
                    onClick = { cityPickerOpen = true },
                    trailing = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
                )

                Spacer(Modifier.height(8.dp))
                PremiumSectionTitle(stringResource(R.string.app_language))
                listOf(
                    "ar" to R.string.arabic,
                    "en" to R.string.english,
                    "tr" to R.string.turkish,
                ).forEach { (tag, label) ->
                    val selected = AppCompatDelegate
                        .getApplicationLocales()
                        .toLanguageTags()
                        .startsWith(tag)

                    PremiumInfoRow(
                        icon = Icons.Default.Language,
                        title = stringResource(label),
                        onClick = {
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(tag)
                            )
                        },
                        trailing = {
                            RadioButton(
                                selected = selected,
                                onClick = {
                                    AppCompatDelegate.setApplicationLocales(
                                        LocaleListCompat.forLanguageTags(tag)
                                    )
                                },
                            )
                        },
                    )
                }

                Spacer(Modifier.height(8.dp))
                PremiumSectionTitle(stringResource(R.string.theme))

                ThemeMode.entries.forEach { mode ->
                    val label = when (mode) {
                        ThemeMode.SYSTEM -> R.string.theme_system
                        ThemeMode.LIGHT -> R.string.theme_light
                        ThemeMode.DARK -> R.string.theme_dark
                    }
                    PremiumInfoRow(
                        icon = when (mode) {
                            ThemeMode.SYSTEM -> Icons.Default.SettingsBrightness
                            ThemeMode.LIGHT -> Icons.Default.LightMode
                            ThemeMode.DARK -> Icons.Default.DarkMode
                        },
                        title = stringResource(label),
                        onClick = { viewModel.setTheme(mode) },
                        trailing = {
                            RadioButton(
                                selected = theme == mode,
                                onClick = { viewModel.setTheme(mode) },
                            )
                        },
                    )
                }

                Spacer(Modifier.height(8.dp))
                PremiumSectionTitle(stringResource(R.string.account_status))

                PremiumInfoRow(
                    icon = Icons.AutoMirrored.Filled.Logout,
                    title = stringResource(R.string.logout),
                    tint = BrandEnergy,
                    onClick = { showLogout = true },
                    trailing = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
                )

                PremiumInfoRow(
                    icon = Icons.Default.DeleteForever,
                    title = stringResource(R.string.delete_account),
                    subtitle = stringResource(R.string.delete_account_body),
                    tint = MaterialTheme.colorScheme.error,
                    onClick = { showDelete = true },
                    trailing = {
                        if (accountAction.deleting) {
                            CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                        } else {
                            Icon(Icons.Default.ChevronRight, contentDescription = null)
                        }
                    },
                )

                accountAction.error?.let { error ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.errorContainer,
                    ) {
                        Text(
                            error,
                            modifier = Modifier.fillMaxWidth().padding(14.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer,
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
                PremiumSectionTitle(stringResource(R.string.about))
                PremiumInfoRow(
                    icon = Icons.Default.Info,
                    title = stringResource(R.string.app_name),
                    subtitle = "${stringResource(R.string.version)} ${com.vibeout.talaa.BuildConfig.VERSION_NAME}",
                    tint = BrandMint,
                )
                Spacer(Modifier.height(20.dp))
            }
        }
    }

    if (showLogout) {
        ConfirmDialog(
            title = stringResource(R.string.logout),
            body = stringResource(R.string.logout_confirm),
            onConfirm = {
                showLogout = false
                viewModel.logout(onLoggedOut)
            },
            onDismiss = { showLogout = false },
        )
    }

    if (showDelete) {
        ConfirmDialog(
            title = stringResource(R.string.delete_account_confirm),
            body = stringResource(R.string.delete_account_body),
            confirmLabel = stringResource(R.string.delete_account),
            destructive = true,
            onConfirm = {
                showDelete = false
                viewModel.deleteAccount(onLoggedOut)
            },
            onDismiss = { showDelete = false },
        )
    }

    if (cityPickerOpen) {
        SimpleListPicker(
            title = stringResource(R.string.choose_city),
            items = cities.map { it.id to it.localizedName(currentLocale) },
            onPick = {
                cityPickerOpen = false
                viewModel.changeCity(it)
            },
            onDismiss = { cityPickerOpen = false },
        )
    }
}
