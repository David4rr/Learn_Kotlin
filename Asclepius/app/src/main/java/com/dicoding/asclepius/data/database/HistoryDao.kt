package com.dicoding.asclepius.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dicoding.asclepius.data.model.HistoryResponse

@Dao
interface HistoryDao {
    @Insert
    fun insertHistory(historyItem: HistoryResponse)

    @Query("SELECT * FROM history")
    fun getAllHistory(): List<HistoryResponse>
}