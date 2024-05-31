package com.davidarrozaqi.storyapp.data.repository

import android.net.Uri
import com.davidarrozaqi.storyapp.data.dto.story.StoryAddResponse
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.data.dto.story.StoryALlResponse
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getAllStories(): Flow<ApiResponse<StoryALlResponse>>

    fun addStory(imageUri: Uri, description: String): Flow<ApiResponse<StoryAddResponse>>
}

