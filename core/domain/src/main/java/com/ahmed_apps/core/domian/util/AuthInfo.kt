package com.ahmed_apps.core.domian.util

/**
 * @author Ahmed Guedmioui
 */
data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)