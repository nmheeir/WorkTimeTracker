package com.example.worktimetracker.data.remote.response

data class DataResponse<T>(
    val _data: T?,
    val _message: String,
    val _success: Boolean
)