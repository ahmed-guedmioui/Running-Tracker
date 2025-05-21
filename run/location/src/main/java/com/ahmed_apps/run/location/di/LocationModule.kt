package com.ahmed_apps.run.location.di

import com.ahmed_apps.run.domain.LocationObserver
import com.ahmed_apps.run.location.AndroidLocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */

val locationModule = module {
    singleOf(::AndroidLocationObserver).bind(LocationObserver::class)
}



























