package com.ahmed_apps.core.presentation.designsystem_wear

import androidx.compose.runtime.Composable
import androidx.wear.compose.material3.ColorScheme
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Typography
import com.ahmed_apps.core.presentation.designsystem.DarkColorScheme
import com.ahmed_apps.core.presentation.designsystem.Poppins

/**
 * @author Ahmed Guedmioui
 */

private fun createColorScheme(): ColorScheme {
    val phoneTheme = DarkColorScheme
    return ColorScheme(
        primary = phoneTheme.primary,
        primaryContainer = phoneTheme.primaryContainer,
        onPrimary = phoneTheme.onPrimary,
        onPrimaryContainer = phoneTheme.onPrimaryContainer,
        secondary = phoneTheme.secondary,
        onSecondary = phoneTheme.onSecondary,
        secondaryContainer = phoneTheme.secondaryContainer,
        onSecondaryContainer = phoneTheme.onSecondaryContainer,
        tertiary = phoneTheme.tertiary,
        onTertiary = phoneTheme.onTertiary,
        tertiaryContainer = phoneTheme.tertiaryContainer,
        onTertiaryContainer = phoneTheme.onTertiaryContainer,
        surface = phoneTheme.surface,
        onSurface = phoneTheme.onSurface,
        surfaceDim = phoneTheme.surfaceVariant,
        onSurfaceVariant = phoneTheme.onSurfaceVariant,
        background = phoneTheme.background,
        error = phoneTheme.error,
        onError = phoneTheme.onError,
        onBackground = phoneTheme.onBackground
    )
}

private fun createTypography(): Typography {
    return Typography(
        defaultFontFamily = Poppins
    )
}

private val WearColors = createColorScheme()
private val WearTypography = createTypography()

@Composable
fun RunningTrackerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = WearColors,
        typography = WearTypography
    ) {
        content()
    }
}


























