package com.davidarrozaqi.storyapp.modules

import com.davidarrozaqi.storyapp.ui.add.AddViewModel
import com.davidarrozaqi.storyapp.ui.auth.AuthViewModel
import com.davidarrozaqi.storyapp.ui.home.HomeViewModel
import com.davidarrozaqi.storyapp.ui.login.LoginViewModel
import com.davidarrozaqi.storyapp.ui.map.MapsViewModel
import com.davidarrozaqi.storyapp.ui.register.RegisterViewModel
import com.davidarrozaqi.storyapp.ui.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { SettingViewModel(get()) }
    viewModel { AddViewModel(get()) }
    viewModel { MapsViewModel(get()) }
}