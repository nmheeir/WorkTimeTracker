package com.example.worktimetracker.domain.repository

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.domain.result.ApiResult
import java.util.Objects

interface CheckRepository {
    suspend fun check (
        checkType : Int,
        token: String
    ) : ApiResult<DataResponse<Any>>
}