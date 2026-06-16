@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.vibeout.talaa.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibeout.talaa.R
import com.vibeout.talaa.core.model.City
import com.vibeout.talaa.core.network.dto.RegisterRequest
import com.vibeout.talaa.core.storage.AppPreferences
import com.vibeout.talaa.data.AppRepository
import com.vibeout.talaa.ui.common.*
import com.vibeout.talaa.ui.components.SimpleListPicker
import com.vibeout.talaa.ui.designsystem.*
import com.vibeout.talaa.ui.theme.BrandEnergy
import com.vibeout.talaa.ui.theme.BrandMint
import com.vibeout.talaa.ui.theme.BrandNight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

enum class SplashDestination { ONBOARDING, LOGIN, HOME }

data class SplashUiState(val loading: Boolean = true, val destination: SplashDestination? = null)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appPreferences: AppPreferences,
    private val repository: AppRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SplashUiState())
    val state: StateFlow<SplashUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val onboarded = appPreferences.onboardingDone.first()
            if (!onboarded) {
                _state.value = SplashUiState(false, SplashDestination.ONBOARDING)
            } else {
                val user = repository.restoreSession()
                _state.value = SplashUiState(false, if (user != null) SplashDestination.HOME else SplashDestination.LOGIN)
            }
        }
    }
}

@Composable
fun SplashScreen(onDestination: (SplashDestination) -> Unit, viewModel: SplashViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(state.destination) { state.destination?.let(onDestination) }

    Box(Modifier.fillMaxSize().background(BrandNight), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            VibeLogoMark(Modifier.size(104.dp), containerColor = Color.White, contentColor = BrandNight)
            Spacer(Modifier.height(28.dp))
            Text(stringResource(R.string.app_name), color = Color.White, style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(8.dp))
            Text(stringResource(R.string.tagline), color = Color.White.copy(alpha = 0.70f), style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(34.dp))
            LinearProgressIndicator(Modifier.width(72.dp), color = BrandEnergy, trackColor = Color.White.copy(alpha = 0.10f))
        }
        Row(
            Modifier.align(Alignment.BottomCenter).padding(bottom = 34.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.size(8.dp).background(BrandMint, CircleShape))
            Text("REAL PLANS. REAL CITY.", color = Color.White.copy(alpha = 0.52f), style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun OnboardingScreen(onDone: () -> Unit) {
    VibeScreen {
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            VibeLogoLockup(stringResource(R.string.app_name), stringResource(R.string.tagline))
            Column(Modifier.padding(vertical = 34.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                VibeHeroCard("VIBEOUT", stringResource(R.string.welcome_title), stringResource(R.string.welcome_body), Modifier.fillMaxWidth())
                BenefitRow(Icons.Default.AutoAwesome, stringResource(R.string.generate_plan), BrandEnergy)
                BenefitRow(Icons.Default.Place, stringResource(R.string.places), BrandMint)
                BenefitRow(Icons.Default.Groups, stringResource(R.string.vibes), MaterialTheme.colorScheme.tertiary)
            }
            VibePrimaryButton(stringResource(R.string.get_started), onDone, Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun BenefitRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, accent: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        Surface(shape = CircleShape, color = accent.copy(alpha = 0.14f), contentColor = accent) {
            Icon(icon, null, Modifier.padding(12.dp).size(22.dp))
        }
        Text(text, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
    }
}

data class AuthUiState(
    val loading: Boolean = false,
    val citiesLoading: Boolean = false,
    val error: String? = null,
    val cities: List<City> = emptyList(),
    val authenticated: Boolean = false,
)

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    fun loadCities() {
        if (_state.value.cities.isNotEmpty()) return
        viewModelScope.launch {
            _state.value = _state.value.copy(citiesLoading = true, error = null)
            runCatching { repository.getCities() }
                .onSuccess { _state.value = _state.value.copy(citiesLoading = false, cities = it) }
                .onFailure { _state.value = _state.value.copy(citiesLoading = false, error = it.message) }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            runCatching { repository.login(email, password) }
                .onSuccess { _state.value = _state.value.copy(loading = false, authenticated = true) }
                .onFailure { _state.value = _state.value.copy(loading = false, error = it.message) }
        }
    }

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            runCatching { repository.register(request) }
                .onSuccess { _state.value = _state.value.copy(loading = false, authenticated = true) }
                .onFailure { _state.value = _state.value.copy(loading = false, error = it.message) }
        }
    }
}

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegister: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var visible by rememberSaveable { mutableStateOf(false) }
    var localError by rememberSaveable { mutableStateOf<String?>(null) }
    val invalidEmail = stringResource(R.string.invalid_email)
    val invalidPassword = stringResource(R.string.invalid_password)

    LaunchedEffect(state.authenticated) { if (state.authenticated) onLoginSuccess() }

    VibeScreen {
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp, vertical = 34.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            VibeLogoLockup(stringResource(R.string.app_name), stringResource(R.string.tagline))
            Spacer(Modifier.height(44.dp))
            Text(stringResource(R.string.login), style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(8.dp))
            Text(stringResource(R.string.welcome_body), color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(28.dp))
            VibeTextField(
                email, { email = it; localError = null }, stringResource(R.string.email), Modifier.fillMaxWidth(), Icons.Default.Email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            )
            Spacer(Modifier.height(14.dp))
            VibeTextField(
                value = password,
                onValueChange = { password = it; localError = null },
                label = stringResource(R.string.password),
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Default.Lock,
                trailingContent = {
                    IconButton(onClick = { visible = !visible }) {
                        Icon(if (visible) Icons.Default.VisibilityOff else Icons.Default.Visibility, null)
                    }
                },
                visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    if (email.isValidEmail() && password.isStrongPassword()) viewModel.login(email, password)
                }),
            )
            (localError ?: state.error)?.let {
                Spacer(Modifier.height(12.dp))
                Surface(shape = RoundedCornerShape(14.dp), color = MaterialTheme.colorScheme.errorContainer) {
                    Text(it, Modifier.fillMaxWidth().padding(12.dp), color = MaterialTheme.colorScheme.onErrorContainer)
                }
            }
            Spacer(Modifier.height(22.dp))
            VibePrimaryButton(
                text = stringResource(R.string.login),
                onClick = {
                    localError = when {
                        !email.isValidEmail() -> invalidEmail
                        !password.isStrongPassword() -> invalidPassword
                        else -> null
                    }
                    if (localError == null) viewModel.login(email.trim(), password)
                },
                loading = state.loading,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(10.dp))
            TextButton(onClick = onRegister, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(stringResource(R.string.no_account) + " " + stringResource(R.string.register), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) { viewModel.loadCities() }
    LaunchedEffect(state.authenticated) { if (state.authenticated) onSuccess() }

    var firstName by rememberSaveable { mutableStateOf("") }
    var displayName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var birthYear by rememberSaveable { mutableStateOf("") }
    var cityId by rememberSaveable { mutableStateOf<String?>(null) }
    var pickerOpen by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(topBar = { VibeTopBar(stringResource(R.string.register), onBack) }) { padding ->
        VibeScreen(Modifier.padding(padding)) {
            Column(
                Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Text(stringResource(R.string.create_account), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
                Text(stringResource(R.string.profile_setup_body), color = MaterialTheme.colorScheme.onSurfaceVariant)
                VibeTextField(firstName, { firstName = it }, stringResource(R.string.first_name), Modifier.fillMaxWidth(), Icons.Default.Person)
                VibeTextField(displayName, { displayName = it }, stringResource(R.string.display_name), Modifier.fillMaxWidth(), Icons.Default.Badge)
                VibeTextField(email, { email = it }, stringResource(R.string.email), Modifier.fillMaxWidth(), Icons.Default.Email, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
                VibeTextField(phone, { phone = it }, stringResource(R.string.phone), Modifier.fillMaxWidth(), Icons.Default.Phone, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
                VibeTextField(
                    password, { password = it }, stringResource(R.string.password), Modifier.fillMaxWidth(), Icons.Default.Lock,
                    trailingContent = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility, null)
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                )
                VibeTextField(
                    birthYear, { birthYear = it.filter(Char::isDigit).take(4) }, stringResource(R.string.birth_year), Modifier.fillMaxWidth(), Icons.Default.Cake,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                OutlinedButton(onClick = { pickerOpen = true }, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(18.dp)) {
                    Icon(Icons.Default.LocationCity, null)
                    Spacer(Modifier.width(8.dp))
                    Text(state.cities.firstOrNull { it.id == cityId }?.localizedName(Locale.getDefault()) ?: stringResource(R.string.choose_city))
                }
                state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                VibePrimaryButton(
                    text = stringResource(R.string.create_account),
                    onClick = {
                        val selectedCity = cityId ?: return@VibePrimaryButton
                        viewModel.register(
                            RegisterRequest(
                                firstName = firstName.trim(),
                                displayName = displayName.trim().takeIf { it.isNotEmpty() },
                                email = email.trim(),
                                phone = phone.trim(),
                                password = password,
                                birthYear = birthYear.toIntOrNull(),
                                cityId = selectedCity,
                                languages = listOf(Locale.getDefault().language),
                                interests = emptyList(),
                            )
                        )
                    },
                    enabled = firstName.length >= 2 && email.isValidEmail() && password.isStrongPassword() && phone.length >= 7 && cityId != null,
                    loading = state.loading,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(24.dp))
            }
        }
    }

    if (pickerOpen) {
        SimpleListPicker(
            title = stringResource(R.string.choose_city),
            items = state.cities.map { it.id to it.localizedName(Locale.getDefault()) },
            onPick = { cityId = it; pickerOpen = false },
            onDismiss = { pickerOpen = false },
        )
    }
}
