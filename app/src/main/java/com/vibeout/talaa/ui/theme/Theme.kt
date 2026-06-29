package com.vibeout.talaa.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = BrandNight,
    onPrimary = Color.White,
    primaryContainer = IndigoContainer,
    onPrimaryContainer = OnIndigoContainer,
    secondary = BrandEnergy,
    onSecondary = Color.White,
    secondaryContainer = EnergyContainer,
    onSecondaryContainer = OnEnergyContainer,
    tertiary = BrandMint,
    onTertiary = Color.White,
    tertiaryContainer = MintContainer,
    onTertiaryContainer = OnMintContainer,
    background = WarmCanvas,
    onBackground = InkPrimary,
    surface = WarmSurface,
    onSurface = InkPrimary,
    surfaceVariant = WarmSurfaceAlt,
    onSurfaceVariant = InkSecondary,
    outline = OutlineWarm,
    outlineVariant = BorderWarm,
    error = Danger,
    onError = Color.White,
    errorContainer = DangerContainer,
    onErrorContainer = OnDangerContainer,
    surfaceTint = BrandNight,
    scrim = ScrimWarm,
    inverseSurface = BrandNight,
    inverseOnSurface = WarmCanvas,
    inversePrimary = BrandMint,
)

private val DarkColors = darkColorScheme(
    primary = BrandEnergy,
    onPrimary = OnEnergyDark,
    primaryContainer = EnergyContainerDark,
    onPrimaryContainer = OnEnergyContainerDark,
    secondary = BrandMint,
    onSecondary = OnMintDark,
    secondaryContainer = MintContainerDark,
    onSecondaryContainer = OnMintContainerDark,
    tertiary = BrandSky,
    onTertiary = Color(0xFF052033),
    tertiaryContainer = SkyContainerDark,
    onTertiaryContainer = OnSkyContainerDark,
    background = DarkCanvas,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceAlt,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline,
    outlineVariant = DarkBorder,
    error = Danger,
    onError = Color.White,
    errorContainer = DangerContainerDark,
    onErrorContainer = OnDangerContainerDark,
    surfaceTint = BrandEnergy,
    scrim = Color.Black,
    inverseSurface = WarmCanvas,
    inverseOnSurface = BrandNight,
    inversePrimary = BrandNight,
)

@Composable
fun VibeOutTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = VibeOutTypography,
        shapes = VibeOutShapes,
        content = content,
    )
}
