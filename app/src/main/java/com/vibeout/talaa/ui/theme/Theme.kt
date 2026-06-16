package com.vibeout.talaa.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Indigo,
    secondary = Coral,
    background = Cream,
    surface = Cream,
    error = Danger,
)

private val DarkColors = darkColorScheme(
    primary = ColorTokens.PrimaryDark,
    secondary = Coral,
    background = DarkBackground,
    surface = DarkSurface,
    error = ColorTokens.ErrorDark,
)

private object ColorTokens {
    val PrimaryDark = androidx.compose.ui.graphics.Color(0xFFA5B4FC)
    val ErrorDark = androidx.compose.ui.graphics.Color(0xFFFCA5A5)
}

@Composable
fun VibeOutTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = VibeOutTypography,
        content = content,
    )
}
