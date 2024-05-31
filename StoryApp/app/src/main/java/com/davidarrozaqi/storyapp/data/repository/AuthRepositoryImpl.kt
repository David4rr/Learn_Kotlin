package com.davidarrozaqi.storyapp.data.repository

import com.davidarrozaqi.storyapp.data.dto.auth.LoginRequest
import com.davidarrozaqi.storyapp.data.dto.auth.LoginResponse
import com.davidarrozaqi.storyapp.data.dto.auth.RegisterRequest
import com.davidarrozaqi.storyapp.data.dto.auth.RegisterResponse
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.data.network.StoryService
import com.davidarrozaqi.storyapp.utils.ConstVal
import com.davidarrozaqi.storyapp.utils.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.lang.Exception

class AuthRepositoryImpl(
    private val storyService: StoryService,
    private val preferenceManager: PreferenceManager
) : AuthRepository {

    override fun login(user: LoginRequest): Flow<ApiResponse<LoginResponse>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = storyService.login(user)
            if (!response.error){
                val userResponse = response.loginResult
                preferenceManager.setLoginPrefs(userResponse)

                reloadKoinModules()
                emit(ApiResponse.Success(response))
            } else {
               emit(ApiResponse.Error(response.message))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    override fun register(user: RegisterRequest): Flow<ApiResponse<RegisterResponse>> = flow{
        try {
            emit(ApiResponse.Loading)
            val response = storyService.register(user)
            if (!response.error){
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (e: Exception){
            e.printStackTrace()
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    override fun logout(): Boolean {
        return try {
            preferenceManager.clearAllPreferences()
            reloadKoinModules()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun reloadKoinModules() {
        unloadKoinModules(ConstVal.koinModules)
        loadKoinModules(ConstVal.koinModules)
    }

}