package com.davidarrozaqi.storyapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.davidarrozaqi.storyapp.data.dto.story.StoryResponse
import com.davidarrozaqi.storyapp.data.repository.StoryRepository
import com.davidarrozaqi.storyapp.utils.PreferenceManager

class HomeViewModel(
    val repository: StoryRepository,
    private val pref: PreferenceManager
) : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    val storyResult: LiveData<PagingData<StoryResponse>> =
        repository.getAllStories(3).cachedIn(viewModelScope)

    private fun getCurrentUserName() {
        _username.value = pref.getName
    }

    init {
        getCurrentUserName()
    }
}