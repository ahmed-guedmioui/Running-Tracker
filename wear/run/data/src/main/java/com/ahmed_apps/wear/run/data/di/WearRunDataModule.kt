package com.ahmed_apps.wear.run.data.di

import com.ahmed_apps.wear.run.data.HealthServicesExerciseTracker
import com.ahmed_apps.wear.run.data.WatchToPhoneConnector
import com.ahmed_apps.wear.run.domain.ExerciseTracker
import com.ahmed_apps.wear.run.domain.PhoneConnector
import com.ahmed_apps.wear.run.domain.RunningTracker
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */

val wearRunDataModule = module {
    singleOf(::HealthServicesExerciseTracker).bind<ExerciseTracker>()
    singleOf(::WatchToPhoneConnector).bind<PhoneConnector>()
    singleOf(::RunningTracker)
    single { get<RunningTracker>().elapsedTime }
}