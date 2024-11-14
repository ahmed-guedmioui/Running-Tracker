package com.ahmed_apps.run.presentation.run_overview

import com.ahmed_apps.run.presentation.run_overview.model.RunUi

/**
 * @author Ahmed Guedmioui
 */
data class RunOverviewState(
    val runs: List<RunUi> = emptyList()
)
