package com.ahmed_apps.core.data.auth

import kotlinx.serialization.Serializable

/**
 * @author Ahmed Guedmioui
 */
@Serializable
data class AuthInfoSerializable(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)
