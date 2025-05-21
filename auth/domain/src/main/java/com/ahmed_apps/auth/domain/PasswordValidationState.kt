package com.ahmed_apps.auth.domain

/**
 * @author Ahmed Guedmioui
 */
data class PasswordValidationState(
    val hasMinimumLength: Boolean = false,
    val hasDigit: Boolean = false,
    val hasUppercaseLetter: Boolean = false,
    val hasLowercaseLetter: Boolean = false,
) {
    val isValidPassword: Boolean
        get() = hasMinimumLength && hasDigit && hasUppercaseLetter && hasLowercaseLetter
}
