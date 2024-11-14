package com.ahmed_apps.run.network

import kotlinx.serialization.Serializable

/**
 * @author Ahmed Guedmioui
 */
@Serializable
data class CreateRunRequest(
    val durationMillis: Long,
    val distanceMeters: Int,
    val epochMillis: Long,
    val lat: Double,
    val long: Double,
    val avgSpeedKmh: Double,
    val maxSpeedKmh: Double,
    val totalElevationMeters: Int,
    val avgHeartRate: Int?,
    val maxHeartRate: Int?,
    val id: String,
)




















