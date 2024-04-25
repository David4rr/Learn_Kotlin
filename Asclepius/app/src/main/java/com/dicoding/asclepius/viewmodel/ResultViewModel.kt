package com.dicoding.asclepius.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.database.HistoryRepository
import com.dicoding.asclepius.data.model.HistoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultViewModel(private val historyRepo: HistoryRepository) : ViewModel() {

    fun saveHistory(imageUri: String, prediction: String, confidenceScore: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            val time = System.currentTimeMillis()
            val historyItem = HistoryResponse(
                id = time,
                imageUri = imageUri,
                prediction = prediction,
                confidenceScore = confidenceScore
            )
            historyRepo.insert(historyItem)
        }
    }
}