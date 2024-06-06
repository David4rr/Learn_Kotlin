package com.davidarrozaqi.storyapp.data.network

import com.davidarrozaqi.storyapp.data.dto.auth.LoginRequest
import com.davidarrozaqi.storyapp.data.dto.auth.LoginResponse
import com.davidarrozaqi.storyapp.data.dto.auth.RegisterRequest
import com.davidarrozaqi.storyapp.data.dto.auth.RegisterResponse
import com.davidarrozaqi.storyapp.data.dto.story.StoryALlResponse
import com.davidarrozaqi.storyapp.data.dto.story.StoryAddResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface StoryService {
    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("stories")
    suspend fun getAllStories(
        @Query("page") page: Int?,
        @Query("size") size: Int?,
    ): StoryALlResponse

    @GET("stories")
    suspend fun getAllWithLocation(
        @Query("location") location: Int = 1
    ) : StoryALlResponse

    @Multipart
    @POST("stories")
    suspend fun addNewStory(
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Double,
        @Part("lon") lon: Double
    ): StoryAddResponse
}