package ru.mirea.kt.ribo.skymate.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = SkyBlue,
    onPrimary = SkySurface,
    primaryContainer = SkyBlueLight,
    onPrimaryContainer = SkyBlueDark,
    inversePrimary = SkyCyan,

    secondary = SkyCyan,
    onSecondary = SkySurface,
    secondaryContainer = SkyCyanLight,
    onSecondaryContainer = SkyBlueDark,

    tertiary = SkyYellow,
    onTertiary = SkyTextPrimary,
    tertiaryContainer = SkyYellowLight,
    onTertiaryContainer = SkyTextPrimary,

    background = SkyBackground,
    onBackground = SkyTextPrimary,

    surface = SkySurface,
    onSurface = SkyTextPrimary,

    surfaceVariant = SkySurfaceVariant,
    onSurfaceVariant = SkyTextSecondary,

    surfaceTint = SkyBlue,

    inverseSurface = SkyTextPrimary,
    inverseOnSurface = SkySurface,

    error = SkyError,
    onError = SkySurface,
    errorContainer = SkyErrorContainer,
    onErrorContainer = SkyError,

    outline = SkyOutline,
    outlineVariant = SkySurfaceVariant,

    scrim = SkyTextPrimary,

    surfaceBright = SkySurface,
    surfaceDim = SkyBackground,
    surfaceContainer = SkySurface,
    surfaceContainerHigh = SkySurface,
    surfaceContainerHighest = SkySurfaceVariant,
    surfaceContainerLow = SkySurface,
    surfaceContainerLowest = SkySurface
)

@Composable
fun SkyMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}