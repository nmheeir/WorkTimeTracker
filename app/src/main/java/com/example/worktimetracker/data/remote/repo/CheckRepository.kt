package com.example.worktimetracker.data.remote.repo

import com.example.worktimetracker.data.remote.request.CheckRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.skydoves.sandwich.ApiResponse

interface CheckRepository {
    suspend fun check (
        checkRequest: CheckRequest,
        token: String
    ) : ApiResponse<DataResponse<Any>>;
}