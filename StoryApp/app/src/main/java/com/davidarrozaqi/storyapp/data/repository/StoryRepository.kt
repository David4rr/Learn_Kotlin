package com.davidarrozaqi.storyapp.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.davidarrozaqi.storyapp.data.dto.story.StoryAddResponse
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.data.dto.story.StoryALlResponse
import com.davidarrozaqi.storyapp.data.dto.story.StoryResponse
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getAllStories(size: Int): LiveData<PagingData<StoryResponse>>

    fun getAllStoriesWithLocation(): Flow<ApiResponse<StoryALlResponse>>

    fun addStory(imageUri: Uri, description: String, lat: Double, long: Double): Flow<ApiResponse<StoryAddResponse>>
}



