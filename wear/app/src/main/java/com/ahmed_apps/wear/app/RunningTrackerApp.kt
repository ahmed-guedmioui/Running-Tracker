package com.ahmed_apps.wear.app

import android.app.Application
import com.ahmed_apps.core.connectivity.data.di.coreConnectivityDataModule
import com.ahmed_apps.wear.app.presentation.di.appModule
import com.ahmed_apps.wear.run.data.di.wearRunDataModule
import com.ahmed_apps.wear.run.presentation.di.wearRunPresentationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * @author Ahmed Guedmioui
 */
class RunningTrackerApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RunningTrackerApp)
            modules(
                appModule,
                wearRunPresentationModule,
                wearRunDataModule,
                coreConnectivityDataModule
            )
        }
    }
}