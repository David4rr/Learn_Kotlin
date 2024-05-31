package com.davidarrozaqi.storyapp.data.dto.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(

    @Json(name = "loginResult")
    val loginResult: LoginResult,

    @Json(name = "error")
    val error: Boolean,

    @Json(name = "message")
    val message: String
)

@JsonClass(generateAdapter = true)
data class LoginResult(

    @Json(name = "name")
    val name: String,

    @Json(name = "userId")
    val userId: String,

    @Json(name = "token")
    val token: String
)
