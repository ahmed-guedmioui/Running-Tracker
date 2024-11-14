package com.ahmed_apps.running_tracker_app

/**
 * @author Ahmed Guedmioui
 */
data class MainState(
    val isLoggedIn: Boolean = false,
    val isCheckingAuth: Boolean = false,
    val showAnalyticsInstallDialog: Boolean = false
)
