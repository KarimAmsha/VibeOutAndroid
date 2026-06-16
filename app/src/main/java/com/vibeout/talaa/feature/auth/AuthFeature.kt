@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.vibeout.talaa.feature.auth

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
import androidx.compose.ui.res.stringResource
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
import com.vibeout.talaa.ui.components.ErrorPane
import com.vibeout.talaa.ui.components.LoadingPane
import com.vibeout.talaa.ui.components.SimpleListPicker
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
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(Icons.Default.Explore, null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(84.dp))
            Spacer(Modifier.height(16.dp))
            Text(stringResource(R.string.app_name), style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onPrimary)
            Text(stringResource(R.string.tagline), color = MaterialTheme.colorScheme.onPrimary)
            Spacer(Modifier.height(24.dp))
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun OnboardingScreen(onDone: () -> Unit) {
    Surface(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize().padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(Icons.Default.AutoAwesome, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(92.dp))
            Spacer(Modifier.height(24.dp))
            Text(stringResource(R.string.welcome_title), style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(12.dp))
            Text(stringResource(R.string.welcome_body), style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(32.dp))
            Button(
                onClick = onDone,
                modifier = Modifier.fillMaxWidth(),
            ) { Text(stringResource(R.string.get_started)) }
        }
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

    LaunchedEffect(state.authenticated) { if (state.authenticated) onLoginSuccess() }

    Column(
        Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(Icons.Default.Explore, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(64.dp))
        Spacer(Modifier.height(16.dp))
        Text(stringResource(R.string.login), style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.email)) },
            leadingIcon = { Icon(Icons.Default.Email, null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            singleLine = true,
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.password)) },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            trailingIcon = {
                IconButton(onClick = { visible = !visible }) {
                    Icon(if (visible) Icons.Default.VisibilityOff else Icons.Default.Visibility, null)
                }
            },
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { viewModel.login(email, password) }),
            singleLine = true,
        )
        (localError ?: state.error)?.let {
            Spacer(Modifier.height(12.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                localError = when {
                    !email.isValidEmail() -> null
                    !password.isStrongPassword() -> null
                    else -> null
                }
                if (email.isValidEmail() && password.isStrongPassword()) viewModel.login(email, password)
            },
            enabled = !state.loading,
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (state.loading) CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
            else Text(stringResource(R.string.login))
        }
        TextButton(onClick = onRegister, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(R.string.no_account) + " " + stringResource(R.string.register))
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

    Scaffold(topBar = { com.vibeout.talaa.ui.components.VibeOutTopBar(stringResource(R.string.register), onBack) }) { padding ->
        Column(
            Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedTextField(firstName, { firstName = it }, Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.first_name)) }, singleLine = true)
            OutlinedTextField(displayName, { displayName = it }, Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.display_name)) }, singleLine = true)
            OutlinedTextField(email, { email = it }, Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.email)) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), singleLine = true)
            OutlinedTextField(phone, { phone = it }, Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.phone)) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), singleLine = true)
            OutlinedTextField(
                password, { password = it }, Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.password)) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = { IconButton(onClick = { passwordVisible = !passwordVisible }) { Icon(if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility, null) } },
                singleLine = true,
            )
            OutlinedTextField(birthYear, { birthYear = it.filter(Char::isDigit).take(4) }, Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.birth_year)) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), singleLine = true)

            OutlinedButton(onClick = { pickerOpen = true }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.LocationCity, null)
                Spacer(Modifier.width(8.dp))
                Text(state.cities.firstOrNull { it.id == cityId }?.localizedName(Locale.getDefault()) ?: stringResource(R.string.choose_city))
            }
            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Button(
                onClick = {
                    val selectedCity = cityId ?: return@Button
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
                enabled = !state.loading && firstName.length >= 2 && email.isValidEmail() && password.isStrongPassword() && phone.length >= 7 && cityId != null,
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (state.loading) CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                else Text(stringResource(R.string.create_account))
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
