package com.ahmed_apps.auth.domain

/**
 * @author Ahmed Guedmioui
 */
class UserDataValidator(
    private val patternValidator: PatternValidator
) {

    fun isValidEmail(email: String): Boolean {
        return patternValidator.matches(email.trim())
    }

    fun isValidPassword(password: String): PasswordValidationState {
        val hasMinimumLength = password.length >= MIN_PASSWORD_LENGTH
        val hasDigit = password.any { it.isDigit() }
        val hasLowercaseLetter = password.any { it.isLowerCase() }
        val hasUppercaseLetter = password.any { it.isUpperCase() }

        return PasswordValidationState(
            hasMinimumLength = hasMinimumLength,
            hasDigit = hasDigit,
            hasLowercaseLetter = hasLowercaseLetter,
            hasUppercaseLetter = hasUppercaseLetter
        )
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 9
    }
}












