package com.ahmed_apps.analytics.presentation

/**
 * @author Ahmed Guedmioui
 */
data class AnalyticsDashboardState(
    val totalDistanceRun: String,
    val totalTimeRun: String,
    val fastestEverRun: String,
    val avrDistancePerRun: String,
    val avrPacePerRun: String,
    val distances: List<Double>
)
