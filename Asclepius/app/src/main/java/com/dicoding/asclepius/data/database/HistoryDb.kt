package com.dicoding.asclepius.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.model.HistoryResponse

@Database(entities = [HistoryResponse::class], version = 1, exportSchema = false)
abstract class HistoryDb : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}