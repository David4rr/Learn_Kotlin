package com.davidarrozaqi.storyapp.data.repository

import com.davidarrozaqi.storyapp.data.dto.auth.LoginRequest
import com.davidarrozaqi.storyapp.data.dto.auth.LoginResponse
import com.davidarrozaqi.storyapp.data.dto.auth.RegisterRequest
import com.davidarrozaqi.storyapp.data.dto.auth.RegisterResponse
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(user: LoginRequest): Flow<ApiResponse<LoginResponse>>

    fun register(user: RegisterRequest): Flow<ApiResponse<RegisterResponse>>

    fun logout(): Boolean

}