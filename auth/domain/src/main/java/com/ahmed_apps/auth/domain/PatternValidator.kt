package com.ahmed_apps.auth.domain

/**
 * @author Ahmed Guedmioui
 */
interface PatternValidator {
    fun matches(value: String): Boolean
}