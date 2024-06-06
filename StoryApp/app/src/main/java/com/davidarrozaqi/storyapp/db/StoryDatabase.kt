package com.davidarrozaqi.storyapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davidarrozaqi.storyapp.data.dto.story.StoryResponse

@Database(
    entities = [StoryResponse::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase(){
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}