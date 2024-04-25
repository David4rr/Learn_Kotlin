package com.dicoding.asclepius.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.api.ApiConfig
import com.dicoding.asclepius.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    val resultNewsItem = MutableLiveData<Event>()

    fun getListNews() {
        viewModelScope.launch {
            resultNewsItem.value = Event.Loading(true)

            val response = try {
                withContext(Dispatchers.IO) {
                    ApiConfig.getApiService().getNews(
                        QUERY_NEWS,
                        QUERY_CATEGORIES,
                        QUERY_LANGUAGE,
                        BuildConfig.API_KEY
                    )
                }
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
                null
            }

            resultNewsItem.value = Event.Loading(false)

            response?.let {
                resultNewsItem.value = Event.Success(it.articles)
            } ?: run {
                resultNewsItem.value = Event.Error(Exception("Failed to fetch data"))
            }
        }
    }

    companion object {
        private const val QUERY_NEWS = "cancer"
        private const val QUERY_CATEGORIES = "health"
        private const val QUERY_LANGUAGE = "en"
    }
}