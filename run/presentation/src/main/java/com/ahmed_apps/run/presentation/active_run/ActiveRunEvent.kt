package com.ahmed_apps.run.presentation.active_run

import com.ahmed_apps.core.presentation.ui.UiText

/**
 * @author Ahmed Guedmioui
 */
sealed interface ActiveRunEvent {
    data class Error(val error: UiText): ActiveRunEvent
    data object RunSaved: ActiveRunEvent
}




















