package com.ahmed_apps.run.network.di

import com.ahmed_apps.core.domian.run.RemoteRunDataSource
import com.ahmed_apps.run.network.KtorRemoteRunDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */

val networkModule = module {
    singleOf(::KtorRemoteRunDataSource).bind<RemoteRunDataSource>()
}