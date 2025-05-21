package com.ahmed_apps.auth.di

import com.ahmed_apps.auth.presentation.login.LoginViewModel
import com.ahmed_apps.auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * @author Ahmed Guedmioui
 */

val authViewModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}



