package com.ahmed_apps.wear.run.presentation.di

import com.ahmed_apps.wear.run.presentation.TrackerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */

val wearRunPresentationModule = module {
    viewModel { TrackerViewModel(get(), get(), get()) }
}