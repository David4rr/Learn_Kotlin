package com.davidarrozaqi.storyapp.data.dto.auth

import retrofit2.http.Field

data class LoginRequest(
    @Field("email")
    val email: String,

    @Field("password")
    val password: String
)

data class RegisterRequest(
    @Field("name")
    val name: String,

    @Field("email")
    val email: String,

    @Field("password")
    val password: String
)