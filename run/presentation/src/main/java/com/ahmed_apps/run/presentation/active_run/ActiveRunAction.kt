package com.ahmed_apps.run.presentation.active_run

/**
 * @author Ahmed Guedmioui
 */
sealed interface ActiveRunAction {
    data object OnToggleRunClick: ActiveRunAction
    data object OnFinishRunClick: ActiveRunAction
    data object OnResumeRunClick: ActiveRunAction
    data object OnBackClick: ActiveRunAction

    data class SubmitLocationPermissionInfo(
        val acceptedLocationPermission: Boolean,
        val showLocationRational: Boolean
    ): ActiveRunAction

    data class SubmitNotificationPermissionInfo(
        val acceptedNotificationPermission: Boolean,
        val showNotificationRational: Boolean
    ): ActiveRunAction

    data object DismissRationalDialog: ActiveRunAction

    class OnRunProcessed(val mapPictureBytes: ByteArray): ActiveRunAction
}



















