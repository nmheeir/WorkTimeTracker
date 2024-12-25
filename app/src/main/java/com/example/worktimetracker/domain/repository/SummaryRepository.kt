package com.example.worktimetracker.domain.repository

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.domain.result.ApiResult
import com.skydoves.sandwich.ApiResponse

interface SummaryRepository {
    suspend fun getMyWorkTimeEachDay(
        token: String,
        start: Long,
        end: Long
    ) : ApiResponse<DataResponse<List<DayWorkTime>>>

    suspend fun getMyPayCheck(
        token: String
    ) : ApiResponse<DataResponse<List<PayCheck>>>

    suspend fun getMyTotalWorkTime(
        token: String,
        start: Long,
        end: Long
    ) : ApiResponse<DataResponse<Long>>
}