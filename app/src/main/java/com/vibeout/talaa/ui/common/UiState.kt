package com.vibeout.talaa.ui.common

sealed interface UiState<out T> {
    data object Idle : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}

fun String.isStrongPassword(): Boolean = length >= 8 && any(Char::isLetter) && any(Char::isDigit)
fun String.isValidEmail(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(trim()).matches()
