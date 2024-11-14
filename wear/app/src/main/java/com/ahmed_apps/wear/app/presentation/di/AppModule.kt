package com.ahmed_apps.wear.app.presentation.di

import com.ahmed_apps.wear.app.RunningTrackerApp
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as RunningTrackerApp).applicationScope
    }
}