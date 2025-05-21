package com.ahmed_apps.run.presentation.run_overview

import com.ahmed_apps.run.presentation.run_overview.model.RunUi

/**
 * @author Ahmed Guedmioui
 */
sealed interface RunOverviewAction {
    data object OnStartRun: RunOverviewAction
    data object OnLogoutClick: RunOverviewAction
    data object OnAnalyticsClick: RunOverviewAction
    data class DeleteRun(val runUi: RunUi): RunOverviewAction
}













