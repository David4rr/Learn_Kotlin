package com.davidarrozaqi.storyapp.modules

import androidx.room.Room
import com.davidarrozaqi.storyapp.db.StoryDatabase
import com.davidarrozaqi.storyapp.utils.ConstVal.DATABASE
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            StoryDatabase::class.java,
            DATABASE
        ).build()
    }

    single {
        get<StoryDatabase>().storyDao()
        get<StoryDatabase>().remoteKeysDao()
    }
}