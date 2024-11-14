package com.ahmed_apps.analytics.data.di

import com.ahmed_apps.analytics.data.RoomAnalyticsRepository
import com.ahmed_apps.analytics.domain.AnalyticsRepository
import com.ahmed_apps.core.database.RunDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */
val analyticsDataModule = module {
    singleOf(::RoomAnalyticsRepository).bind<AnalyticsRepository>()
    single {
        get<RunDatabase>().analyticsDao
    }
}