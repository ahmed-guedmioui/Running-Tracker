package com.ahmed_apps.auth.presentation.login

/**
 * @author Ahmed Guedmioui
 */
sealed interface LoginAction {
    data object OnTogglePasswordVisibilityClick: LoginAction
    data object OnLoginClick: LoginAction
    data object ObRegisterClick: LoginAction
}










