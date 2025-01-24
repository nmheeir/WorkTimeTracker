package com.example.worktimetracker.domain.repository

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.skydoves.sandwich.ApiResponse

interface AuthRepository {
    suspend fun login(
        username: String,
        password: String,
        deviceToken: String
    ): ApiResponse<DataResponse<Token>>
}