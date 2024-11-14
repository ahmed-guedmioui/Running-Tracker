package com.ahmed_apps.core.data.di

import com.ahmed_apps.core.data.run.OfflineFirstRunRepository
import com.ahmed_apps.core.data.auth.EncryptedSessionStorage
import com.ahmed_apps.core.data.networking.HttpClientFactory
import com.ahmed_apps.core.domian.run.RunRepository
import com.ahmed_apps.core.domian.util.SessionStorage
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */
val coreDataModule = module {
    single {
        HttpClientFactory(get()).build(CIO.create())
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()

    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()
}