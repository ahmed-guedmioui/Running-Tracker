package com.ahmed_apps.run.presentation.active_run

import com.ahmed_apps.core.domian.location.Location
import com.ahmed_apps.run.domain.RunData
import kotlin.time.Duration

/**
 * @author Ahmed Guedmioui
 */
data class ActiveRunState(
    val elapsedTime: Duration = Duration.ZERO,
    val runData: RunData = RunData(),
    val shouldTrack: Boolean = false,
    val hasStartedRunning: Boolean = false,
    val currentLocation: Location? = null,
    val isRunFinished: Boolean = false,
    val isSavingRun: Boolean = false,
    val showLocationRationale: Boolean = false,
    val showNotificationRationale: Boolean = false,
)





















