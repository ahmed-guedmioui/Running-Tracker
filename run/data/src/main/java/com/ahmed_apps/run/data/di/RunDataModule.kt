package com.ahmed_apps.run.data.di

import com.ahmed_apps.core.domian.run.SyncRunScheduler
import com.ahmed_apps.run.data.SyncRunWorkerScheduler
import com.ahmed_apps.run.data.conctivity.PhoneToWatchConnector
import com.ahmed_apps.run.data.workers.CreateRunWorker
import com.ahmed_apps.run.data.workers.FetchRunWorker
import com.ahmed_apps.run.data.workers.DeleteRunWorker
import com.ahmed_apps.run.domain.WatchConnector
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */

val runDataModule = module {
    singleOf(::CreateRunWorker)
    singleOf(::FetchRunWorker)
    singleOf(::DeleteRunWorker)
    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
    singleOf(::PhoneToWatchConnector).bind<WatchConnector>()
}












