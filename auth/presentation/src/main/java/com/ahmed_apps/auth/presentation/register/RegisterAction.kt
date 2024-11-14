package com.ahmed_apps.auth.presentation.register

/**
 * @author Ahmed Guedmioui
 */
sealed interface RegisterAction {
    data object OnTogglePasswordVisibilityClick: RegisterAction
    data object OnLoginClick: RegisterAction
    data object ObRegisterClick: RegisterAction
}

















