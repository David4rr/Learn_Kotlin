package com.davidarrozaqi.storyapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.data.dto.story.StoryALlResponse
import com.davidarrozaqi.storyapp.data.repository.StoryRepository
import com.davidarrozaqi.storyapp.utils.PreferenceManager
import kotlinx.coroutines.launch

class HomeViewModel(
    val repository: StoryRepository,
    val pref: PreferenceManager
) : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private var  _storyResult = MutableLiveData<ApiResponse<StoryALlResponse>>()
    val storyResult: LiveData<ApiResponse<StoryALlResponse>> = _storyResult

    fun getCurrentUserName(){
        _username.value = pref.getName
    }

    fun getAllStories(){
        viewModelScope.launch {
            repository.getAllStories().collect{
                _storyResult.value = it
            }
        }
    }

    init {
        getCurrentUserName()
    }
}