package com.dicoding.asclepius.utils

sealed class Event {
    data class Success<out T>(val data: T) : Event()
    data class Error(val exception: Throwable) : Event()
    data class Loading(val isLoading: Boolean) : Event()
}