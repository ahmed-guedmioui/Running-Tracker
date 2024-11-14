package com.ahmed_apps.wear.run.presentation

/**
 * @author Ahmed Guedmioui
 */
sealed interface TrackerAction {
    data object OnToggleRunClick: TrackerAction
    data object OnFinishRunClick: TrackerAction
    data class OnBodySensorPermissionResult(val isGranted: Boolean): TrackerAction
    data class OnEnterAmbientMode(val burnInProtectionRequired: Boolean): TrackerAction
    data object OnExitAmbientMode: TrackerAction
}