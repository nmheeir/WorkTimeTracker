package com.example.worktimetracker.domain.repository

import com.example.worktimetracker.data.remote.request.CheckRequest
import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.domain.result.ApiResult
import com.skydoves.sandwich.ApiResponse

interface CheckRepository {
    suspend fun check (
        checkRequest: CheckRequest,
        token: String
    ) : ApiResponse<DataResponse<Any>>;
}