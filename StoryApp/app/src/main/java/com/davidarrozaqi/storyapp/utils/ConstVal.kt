package com.davidarrozaqi.storyapp.utils

import com.davidarrozaqi.storyapp.R
import com.davidarrozaqi.storyapp.modules.networkModule
import com.davidarrozaqi.storyapp.modules.repositoryModule
import com.davidarrozaqi.storyapp.modules.viewModelModule

object ConstVal {

    val koinModules = listOf(
        networkModule,
        repositoryModule,
        viewModelModule,
    )

    val bottomBar = listOf(R.id.homeFragment, R.id.settingsFragment)

    const val BASE_URL = "https://story-api.dicoding.dev/v1/"

    const val PREFS_NAME = "story_prefs"
    const val KEY_TOKEN = "auth_token"
    const val KEY_NAME = "auth_name"

    const val MAXIMAL_SIZE = 1000000
}