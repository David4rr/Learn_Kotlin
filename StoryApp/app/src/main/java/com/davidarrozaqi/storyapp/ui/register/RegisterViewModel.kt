package com.davidarrozaqi.storyapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidarrozaqi.storyapp.data.dto.auth.RegisterRequest
import com.davidarrozaqi.storyapp.data.dto.auth.RegisterResponse
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.data.repository.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repo: AuthRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<ApiResponse<RegisterResponse>>()
    val registerResult: LiveData<ApiResponse<RegisterResponse>> = _registerResult

    fun register(user: RegisterRequest) {
        viewModelScope.launch {
            repo.register(user).collect {
                _registerResult.value = it
            }
        }
    }
}