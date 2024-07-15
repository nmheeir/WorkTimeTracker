package com.example.worktimetracker.domain.result

sealed class ApiResult<out T>(val data: T? = null) {
    data class Success<out T>(val response: T) : ApiResult<T>()
    data class Error<out T>(val response: T) : ApiResult<T>()
    data class NetworkError(val message: String) : ApiResult<Nothing>()
}