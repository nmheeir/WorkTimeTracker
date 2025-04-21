package com.example.worktimetracker.data.remote.response

data class DataResponse<T>(
    val data: T?,
    val message: String,
    val success: Boolean,
    val statusCode: String
)