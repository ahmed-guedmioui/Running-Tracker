package com.ahmed_apps.core.presentation.designsystem

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * @author Ahmed Guedmioui
 */
val DarkColorScheme = darkColorScheme(
    primary = RunningTrackerGreen,
    background = RunningTrackerBlack,
    surface = RunningTrackerDarkGray,
    secondary = RunningTrackerWhite,
    tertiary = RunningTrackerWhite,
    primaryContainer = RunningTrackerGreen30,
    onPrimary = RunningTrackerBlack,
    onBackground = RunningTrackerWhite,
    onSurface = RunningTrackerWhite,
    onSurfaceVariant = RunningTrackerGray,
    error = RunningTrackerDarkRed,
    errorContainer = RunningTrackerDarkRed5
)

@Composable
fun RunningTrackerTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}