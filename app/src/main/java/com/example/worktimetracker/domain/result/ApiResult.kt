package com.example.worktimetracker.domain.result

sealed class ApiResult<out T>(val data: T? = null) {
    data class Success<out T>(val response: T? = null) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}