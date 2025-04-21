package com.example.worktimetracker.data.remote.repo

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.skydoves.sandwich.ApiResponse

interface AuthRepository {
    suspend fun login(
        username: String,
        password: String,
        deviceToken: String
    ): ApiResponse<DataResponse<Token>>

    suspend fun requestPasswordReset(
        email: String
    ): ApiResponse<DataResponse<Unit>>

    suspend fun resetPassword(
        token: String,
        newPassword: String
    ): ApiResponse<DataResponse<Unit>>
}