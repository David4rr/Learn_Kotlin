package com.example.xgram.data.api

import com.example.xgram.data.response.DetailUserResponse
import com.example.xgram.data.response.UserResponse
import com.example.xgram.data.response.UserResponseItems
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchUser(
        @Query("q") q: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String
    ): Call<List<UserResponseItems>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String
    ): Call<List<UserResponseItems>>
}