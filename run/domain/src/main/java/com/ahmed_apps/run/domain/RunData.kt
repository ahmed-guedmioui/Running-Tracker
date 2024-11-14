package com.ahmed_apps.run.domain

import com.ahmed_apps.core.domian.location.Location
import com.ahmed_apps.core.domian.location.LocationTimestamp
import kotlin.time.Duration

/**
 * @author Ahmed Guedmioui
 */
data class RunData(
    val distanceMeters: Int = 0,
    val pace: Duration = Duration.ZERO,
    val locations: List<List<LocationTimestamp>> = emptyList(),
    val heartRates: List<Int> = emptyList()
)
