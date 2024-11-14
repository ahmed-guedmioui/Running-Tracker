package com.ahmed_apps.auth.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * @author Ahmed Guedmioui
 */
class UserDataValidatorTest {

    private lateinit var userDataValidator: UserDataValidator

    @BeforeEach
    fun setUp() {
        userDataValidator = UserDataValidator(
            patternValidator = object : PatternValidator {
                override fun matches(value: String): Boolean {
                    return true
                }
            }
        )
    }

    @ParameterizedTest
    @CsvSource(
        "Password123, true",
        "password123, false",
        "354523, false",
        "Passwod1-23, true",
        "TT4646GGFH, false",
    )
    fun testIsValidPassword(password: String, expected: Boolean) {
        val state = userDataValidator.isValidPassword(password)

        assertThat(state.isValidPassword).isEqualTo(expected)
    }
}