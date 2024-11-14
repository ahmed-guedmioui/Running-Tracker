package com.ahmed_apps.running_tracker_app

import android.app.Application
import android.content.Context
import com.ahmed_apps.auth.data.di.authDataModule
import com.ahmed_apps.auth.di.authViewModelModule
import com.ahmed_apps.core.connectivity.data.di.coreConnectivityDataModule
import com.ahmed_apps.core.data.di.coreDataModule
import com.ahmed_apps.core.database.di.databaseModule
import com.ahmed_apps.run.data.di.runDataModule
import com.ahmed_apps.run.di.runPresentationModule
import com.ahmed_apps.run.location.di.locationModule
import com.ahmed_apps.run.network.di.networkModule
import com.ahmed_apps.running_tracker_app.di.appModule
import com.google.android.play.core.splitcompat.SplitCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * @author Ahmed Guedmioui
 */
class RunningTrackerApp: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RunningTrackerApp)
            workManagerFactory()
            modules(
                appModule,
                authDataModule,
                authViewModelModule,
                runPresentationModule,
                coreDataModule,
                locationModule,
                databaseModule,
                networkModule,
                runDataModule,
                coreConnectivityDataModule
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}





















