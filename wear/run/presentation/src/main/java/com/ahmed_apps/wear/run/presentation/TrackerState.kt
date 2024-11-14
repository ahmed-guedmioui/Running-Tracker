package com.ahmed_apps.wear.run.presentation

import kotlin.time.Duration

/**
 * @author Ahmed Guedmioui
 */
data class TrackerState(
    val elapsedDuration: Duration = Duration.ZERO,
    val distanceMeters: Int = 0,
    val heartRate: Int = 0,
    val isTrackable: Boolean = false,
    val hasStartedRunning: Boolean = false,
    val isConnectedPhoneNearby: Boolean = false,
    val isRunActive: Boolean = false,
    val canTrackHeartRate: Boolean = false,
    val isAmbientMode: Boolean = false,
    val burnInProtectionRequired: Boolean = false
)
