package com.ahmed_apps.wear.run.presentation

import com.ahmed_apps.core.presentation.ui.UiText

/**
 * @author Ahmed Guedmioui
 */
sealed interface TrackerEvent {
    data object RunFinished : TrackerEvent
    data class Error(val message: UiText) : TrackerEvent
}