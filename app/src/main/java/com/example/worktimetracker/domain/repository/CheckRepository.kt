package com.example.worktimetracker.domain.repository

import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.domain.result.ApiResult

interface CheckRepository {
    suspend fun check (
        checkType : Int,
        token: String
    ) : ApiResult<DataResponse<Any>>;
    suspend fun getCheckWithTime(
        token: String,
        start: Long?,
        end: Long ?
    ) : ApiResult<DataResponse<List<Check>>>
    suspend fun getCheckWithDate(
        token: String,
        year: Int?,
        month: Int?,
        day: Int?,
    ) : ApiResult<DataResponse<List<Check>>>
}