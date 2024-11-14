package com.ahmed_apps.analytics.domain

/**
 * @author Ahmed Guedmioui
 */
interface AnalyticsRepository {
    suspend fun getAnalyticsValues(): AnalyticsValues
}