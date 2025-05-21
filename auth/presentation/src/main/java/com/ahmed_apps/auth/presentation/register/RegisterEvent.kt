package com.ahmed_apps.auth.presentation.register

import com.ahmed_apps.core.presentation.ui.UiText

/**
 * @author Ahmed Guedmioui
 */
sealed interface RegisterEvent {
    data object RegistrationSuccess: RegisterEvent
    data class Error(val error: UiText): RegisterEvent
}

















