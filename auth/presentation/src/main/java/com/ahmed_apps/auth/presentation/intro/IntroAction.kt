package com.ahmed_apps.auth.presentation.intro

/**
 * @author Ahmed Guedmioui
 */
sealed interface IntroAction {
    data object OnSignInClick: IntroAction
    data object OnSignUpClick: IntroAction
}