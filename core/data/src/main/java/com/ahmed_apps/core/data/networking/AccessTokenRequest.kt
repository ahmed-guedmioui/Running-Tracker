package com.ahmed_apps.core.data.networking

import kotlinx.serialization.Serializable

/**
 * @author Ahmed Guedmioui
 */
@Serializable
data class AccessTokenRequest(
    val refreshToken: String,
    val userId: String
)
