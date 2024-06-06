package com.davidarrozaqi.storyapp.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.data.dto.story.StoryALlResponse
import com.davidarrozaqi.storyapp.data.repository.StoryRepository
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _storyResult = MutableLiveData<ApiResponse<StoryALlResponse>>()
    val storyResult: LiveData<ApiResponse<StoryALlResponse>> = _storyResult

    fun getAllStoryWithLocation() {
        viewModelScope.launch {
            repository.getAllStoriesWithLocation().collect {
                _storyResult.value = it
            }
        }
    }
}