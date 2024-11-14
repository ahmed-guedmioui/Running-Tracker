package com.ahmed_apps.auth.data.di

import com.ahmed_apps.auth.data.AuthRepositoryImpl
import com.ahmed_apps.auth.data.EmailPatternValidator
import com.ahmed_apps.auth.domain.AuthRepository
import com.ahmed_apps.auth.domain.UserDataValidator
import com.ahmed_apps.auth.domain.PatternValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}




















