package com.example.worktimetracker.domain.repository

import com.example.worktimetracker.data.remote.request.UserUpdateRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.domain.result.ApiResult
import com.skydoves.sandwich.ApiResponse

interface UserRepository {

    suspend fun getUserByUsername(
        userName: String
    ): ApiResponse<DataResponse<User>>

    suspend fun uploadAvatar(
        token: String,
        avatarUrl: String
    ): ApiResponse<DataResponse<User>>
}