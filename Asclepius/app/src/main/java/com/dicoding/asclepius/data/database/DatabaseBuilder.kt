package com.dicoding.asclepius.data.database

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    @Volatile
    private var INSTANCE: HistoryDb? = null

    fun getDatabase(context: Context): HistoryDb {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                HistoryDb::class.java,
                "history_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}