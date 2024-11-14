package com.ahmed_apps.auth.presentation.login

import com.ahmed_apps.core.presentation.ui.UiText

/**
 * @author Ahmed Guedmioui
 */
sealed interface LoginEvent {
    data object LoginSuccess: LoginEvent
    data class Error(val error: UiText): LoginEvent
}

















