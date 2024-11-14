package com.ahmed_apps.analytics.presentation.di

import com.ahmed_apps.analytics.presentation.AnalyticsDashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */

val analyticsPresentationModule = module {
    viewModelOf(::AnalyticsDashboardViewModel)
}