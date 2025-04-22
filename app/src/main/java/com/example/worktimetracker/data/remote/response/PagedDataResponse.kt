package com.example.worktimetracker.data.remote.response

data class PagedDataResponse<T>(
    val data: T?,
    val message: String,
    val success: Boolean,
    val statusCode: String,
    val pageNumber: Int,
    val pageSize: Int,
    val totalItems: Int,
    val totalPages: Int,
)
