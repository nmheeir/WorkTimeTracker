package com.example.worktimetracker.domain.repository.remote

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.domain.result.ApiResult

interface AuthRepository {
    suspend fun login(
        username: String,
        password: String
    ): ApiResult<DataResponse<Token>>
}