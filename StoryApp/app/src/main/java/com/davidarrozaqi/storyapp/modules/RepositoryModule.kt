package com.davidarrozaqi.storyapp.modules

import com.davidarrozaqi.storyapp.data.repository.AuthRepository
import com.davidarrozaqi.storyapp.data.repository.AuthRepositoryImpl
import com.davidarrozaqi.storyapp.data.repository.StoryRepository
import com.davidarrozaqi.storyapp.data.repository.StoryRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<StoryRepository> {StoryRepositoryImpl(get())}
}