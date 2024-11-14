package com.ahmed_apps.analytics.domain

import kotlin.time.Duration


/**
 * @author Ahmed Guedmioui
 */
data class AnalyticsValues(
    val totalDistanceRun: Int,
    val totalTimeRun: Duration,
    val fastestEverRun: Double,
    val avrDistancePerRun: Double,
    val avrPacePerRun: Double,
    val distances: List<Int>
)
