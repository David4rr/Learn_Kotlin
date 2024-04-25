package com.dicoding.asclepius.data.database

import androidx.annotation.WorkerThread
import com.dicoding.asclepius.data.model.HistoryResponse

class HistoryRepository(private val historyDao: HistoryDao) {

    @WorkerThread
    fun insert(historyItem: HistoryResponse) {
        historyDao.insertHistory(historyItem)
    }

    fun getAllHistory(): List<HistoryResponse> {
        return historyDao.getAllHistory()
    }
}