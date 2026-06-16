package com.vibeout.talaa.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = BrandNight,
    secondary = BrandEnergy,
    tertiary = BrandMint,
    background = WarmCanvas,
    surface = WarmSurface,
    surfaceVariant = WarmSurfaceAlt,
    onBackground = InkPrimary,
    onSurface = InkPrimary,
    onSurfaceVariant = InkSecondary,
    outlineVariant = BorderWarm,
    error = Danger,
)

private val DarkColors = darkColorScheme(
    primary = BrandEnergy,
    secondary = BrandMint,
    tertiary = BrandSky,
    background = DarkCanvas,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceAlt,
    outlineVariant = DarkBorder,
    error = Danger,
)

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
