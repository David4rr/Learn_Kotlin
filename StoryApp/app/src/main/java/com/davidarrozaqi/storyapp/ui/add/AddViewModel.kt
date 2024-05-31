package com.davidarrozaqi.storyapp.ui.add

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.data.dto.story.StoryAddResponse
import com.davidarrozaqi.storyapp.data.repository.StoryRepository
import kotlinx.coroutines.launch

class AddViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _upLoadStoryResult =
        MutableLiveData<ApiResponse<StoryAddResponse>>()
    val uploadStoryResult: LiveData<ApiResponse<StoryAddResponse>> = _upLoadStoryResult

    fun uploadStory(imageUri: Uri, description: String){
        viewModelScope.launch {
            repository.addStory(imageUri, description).collect {
                _upLoadStoryResult.value = it
            }
        }
    }
}