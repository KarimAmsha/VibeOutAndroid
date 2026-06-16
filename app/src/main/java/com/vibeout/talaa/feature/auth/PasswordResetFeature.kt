@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.vibeout.talaa.feature.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibeout.talaa.R
import com.vibeout.talaa.data.AppRepository
import com.vibeout.talaa.ui.common.isStrongPassword
import com.vibeout.talaa.ui.common.isValidEmail
import com.vibeout.talaa.ui.designsystem.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class PasswordResetStep {
    EMAIL,
    CODE,
    NEW_PASSWORD,
    SUCCESS,
}

data class PasswordResetUiState(
    val step: PasswordResetStep = PasswordResetStep.EMAIL,
    val loading: Boolean = false,
    val email: String = "",
    val resetToken: String? = null,
    val message: String? = null,
    val error: String? = null,
)

@HiltViewModel
class PasswordResetViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(PasswordResetUiState())
    val state: StateFlow<PasswordResetUiState> = _state.asStateFlow()

    fun requestCode(email: String, locale: String?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true,
                email = email.trim().lowercase(),
                error = null,
            )

            runCatching {
                repository.forgotPassword(email, locale)
            }.onSuccess { response ->
                _state.value = _state.value.copy(
                    step = PasswordResetStep.CODE,
                    loading = false,
                    message = response.message,
                    error = null,
                )
            }.onFailure { error ->
                _state.value = _state.value.copy(
                    loading = false,
                    error = error.message,
                )
            }
        }
    }

    fun verifyCode(code: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true,
                error = null,
            )

            runCatching {
                repository.verifyResetCode(
                    email = _state.value.email,
                    code = code,
                )
            }.onSuccess { response ->
                _state.value = _state.value.copy(
                    step = PasswordResetStep.NEW_PASSWORD,
                    loading = false,
                    resetToken = response.resetToken,
                    error = null,
                )
            }.onFailure { error ->
                _state.value = _state.value.copy(
                    loading = false,
                    error = error.message,
                )
            }
        }
    }

    fun resetPassword(newPassword: String) {
        val resetToken = _state.value.resetToken ?: return

        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true,
                error = null,
            )

            runCatching {
                repository.resetPassword(
                    email = _state.value.email,
                    resetToken = resetToken,
                    newPassword = newPassword,
                )
            }.onSuccess { response ->
                _state.value = _state.value.copy(
                    step = PasswordResetStep.SUCCESS,
                    loading = false,
                    message = response.message,
                    error = null,
                )
            }.onFailure { error ->
                _state.value = _state.value.copy(
                    loading = false,
                    error = error.message,
                )
            }
        }
    }

    fun backOneStep(): Boolean {
        _state.value = when (_state.value.step) {
            PasswordResetStep.EMAIL -> return false
            PasswordResetStep.CODE -> PasswordResetUiState(
                step = PasswordResetStep.EMAIL,
                email = _state.value.email,
            )
            PasswordResetStep.NEW_PASSWORD -> _state.value.copy(
                step = PasswordResetStep.CODE,
                resetToken = null,
                error = null,
            )
            PasswordResetStep.SUCCESS -> return false
        }
        return true
    }
}

@Composable
fun PasswordResetScreen(
    onBack: () -> Unit,
    onCompleted: () -> Unit,
    viewModel: PasswordResetViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val locale = LocalConfiguration.current.locales[0].language
    var email by rememberSaveable { mutableStateOf("") }
    var code by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            VibeTopBar(
                title = stringResource(R.string.reset_password_title),
                onBack = {
                    if (!viewModel.backOneStep()) onBack()
                },
            )
        },
    ) { padding ->
        VibeScreen(Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                val heroIcon = when (state.step) {
                    PasswordResetStep.EMAIL -> Icons.Default.Email
                    PasswordResetStep.CODE -> Icons.Default.MarkEmailRead
                    PasswordResetStep.NEW_PASSWORD -> Icons.Default.Key
                    PasswordResetStep.SUCCESS -> Icons.Default.Lock
                }

                PremiumAccentCard(
                    title = when (state.step) {
                        PasswordResetStep.EMAIL ->
                            stringResource(R.string.forgot_password)
                        PasswordResetStep.CODE ->
                            stringResource(R.string.enter_reset_code)
                        PasswordResetStep.NEW_PASSWORD ->
                            stringResource(R.string.create_new_password)
                        PasswordResetStep.SUCCESS ->
                            stringResource(R.string.password_reset_success)
                    },
                    body = when (state.step) {
                        PasswordResetStep.EMAIL ->
                            stringResource(R.string.forgot_password_body)
                        PasswordResetStep.CODE ->
                            stringResource(
                                R.string.reset_code_sent_to,
                                state.email,
                            )
                        PasswordResetStep.NEW_PASSWORD ->
                            stringResource(R.string.new_password_body)
                        PasswordResetStep.SUCCESS ->
                            stringResource(R.string.password_reset_success_body)
                    },
                    icon = heroIcon,
                )

                Spacer(Modifier.height(24.dp))

                when (state.step) {
                    PasswordResetStep.EMAIL -> {
                        VibeTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = stringResource(R.string.email),
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = Icons.Default.Email,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                            ),
                        )
                        Spacer(Modifier.height(18.dp))
                        VibePrimaryButton(
                            text = stringResource(R.string.send_reset_code),
                            onClick = {
                                viewModel.requestCode(email, locale)
                            },
                            enabled = email.isValidEmail(),
                            loading = state.loading,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    PasswordResetStep.CODE -> {
                        VibeTextField(
                            value = code,
                            onValueChange = {
                                code = it.filter(Char::isDigit).take(6)
                            },
                            label = stringResource(R.string.reset_code),
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = Icons.Default.Key,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ),
                        )
                        Spacer(Modifier.height(18.dp))
                        VibePrimaryButton(
                            text = stringResource(R.string.verify_code),
                            onClick = { viewModel.verifyCode(code) },
                            enabled = code.length == 6,
                            loading = state.loading,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        TextButton(
                            onClick = {
                                viewModel.requestCode(state.email, locale)
                            },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                stringResource(R.string.resend_code),
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }

                    PasswordResetStep.NEW_PASSWORD -> {
                        VibeTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = stringResource(R.string.new_password),
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = Icons.Default.Lock,
                            trailingContent = {
                                IconButton(
                                    onClick = {
                                        passwordVisible = !passwordVisible
                                    },
                                ) {
                                    Icon(
                                        if (passwordVisible) {
                                            Icons.Default.VisibilityOff
                                        } else {
                                            Icons.Default.Visibility
                                        },
                                        contentDescription = null,
                                    )
                                }
                            },
                            visualTransformation =
                                if (passwordVisible) {
                                    VisualTransformation.None
                                } else {
                                    PasswordVisualTransformation()
                                },
                        )
                        Spacer(Modifier.height(14.dp))
                        VibeTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = stringResource(
                                R.string.confirm_new_password,
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = Icons.Default.Lock,
                            visualTransformation =
                                if (passwordVisible) {
                                    VisualTransformation.None
                                } else {
                                    PasswordVisualTransformation()
                                },
                        )
                        Spacer(Modifier.height(18.dp))
                        VibePrimaryButton(
                            text = stringResource(R.string.reset_password),
                            onClick = {
                                viewModel.resetPassword(password)
                            },
                            enabled =
                                password.isStrongPassword() &&
                                    password == confirmPassword,
                            loading = state.loading,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    PasswordResetStep.SUCCESS -> {
                        VibePrimaryButton(
                            text = stringResource(R.string.back_to_login),
                            onClick = onCompleted,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }

                state.message
                    ?.takeIf {
                        state.step != PasswordResetStep.SUCCESS &&
                            it.isNotBlank()
                    }
                    ?.let {
                        Spacer(Modifier.height(14.dp))
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                        ) {
                            Text(
                                text = it,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                color =
                                    MaterialTheme.colorScheme
                                        .onPrimaryContainer,
                            )
                        }
                    }

                state.error?.let {
                    Spacer(Modifier.height(14.dp))
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.errorContainer,
                    ) {
                        Text(
                            text = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            color =
                                MaterialTheme.colorScheme.onErrorContainer,
                        )
                    }
                }
            }
        }
    }
}
