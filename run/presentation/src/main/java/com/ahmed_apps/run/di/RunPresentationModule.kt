package com.ahmed_apps.run.di

import com.ahmed_apps.run.domain.RunningTracker
import com.ahmed_apps.run.presentation.active_run.ActiveRunViewModel
import com.ahmed_apps.run.presentation.run_overview.RunOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */

val runPresentationModule = module {
    singleOf(::RunningTracker)
    single { get<RunningTracker>().elapsedTime }

    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)
}



