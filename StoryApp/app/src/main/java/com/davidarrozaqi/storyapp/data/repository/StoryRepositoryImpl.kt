package com.davidarrozaqi.storyapp.data.repository

import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.davidarrozaqi.storyapp.data.dto.story.StoryAddResponse
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.data.dto.story.StoryALlResponse
import com.davidarrozaqi.storyapp.data.dto.story.StoryResponse
import com.davidarrozaqi.storyapp.data.network.StoryService
import com.davidarrozaqi.storyapp.data.paging.StoryRemoteMediator
import com.davidarrozaqi.storyapp.db.StoryDatabase
import com.davidarrozaqi.storyapp.utils.ext.reduceFileImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class StoryRepositoryImpl(
    private val storyService: StoryService,
    private val storyDatabase: StoryDatabase
) : StoryRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getAllStories(size: Int): LiveData<PagingData<StoryResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = size
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, storyService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    override fun getAllStoriesWithLocation(): Flow<ApiResponse<StoryALlResponse>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = storyService.getAllWithLocation()
            if (!response.error) {
                emit(ApiResponse.Success(response))
            } else {
                throw Exception()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    override fun addStory(
        imageUri: Uri,
        description: String,
        lat: Double,
        long: Double
    ): Flow<ApiResponse<StoryAddResponse>> =
        flow {
            try {
                emit(ApiResponse.Loading)
                val photo = imageUri.toFile().reduceFileImage()
                val requestImageFile = photo.asRequestBody("image/*".toMediaType())
                val requestBody = description.toRequestBody("text/plain".toMediaType())
                val multipartBody =
                    MultipartBody.Part.createFormData("photo", photo.name, requestImageFile)

                val response = storyService.addNewStory(multipartBody, requestBody, lat, long)

                if (!response.error) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
}