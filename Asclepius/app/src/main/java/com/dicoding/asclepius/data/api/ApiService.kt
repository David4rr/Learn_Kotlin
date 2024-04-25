package com.dicoding.asclepius.data.api

import com.dicoding.asclepius.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @JvmSuppressWildcards
    @GET("/v2/top-headlines")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}