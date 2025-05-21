package com.ahmed_apps.analytics.presentation

/**
 * @author Ahmed Guedmioui
 */
sealed interface AnalyticsAction {
    data object OnBackClick: AnalyticsAction
}