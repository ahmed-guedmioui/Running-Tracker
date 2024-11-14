package com.ahmed_apps.core.data.networking

import kotlinx.serialization.Serializable

/**
 * @author Ahmed Guedmioui
 */
@Serializable
data class AccessTokenResponse(
    val accessToken: String,
    val expirationTimestamp: Long
)
