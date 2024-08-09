package com.example.worktimetracker.domain.repository

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.domain.result.ApiResult

interface SummaryRepository {
    suspend fun getMyWorkTimeEachDay(
        token: String,
        start: Long,
        end: Long
    ) : ApiResult<DataResponse<List<DayWorkTime>>>

    suspend fun getMyPayCheck(
        token: String
    ) : ApiResult<DataResponse<List<PayCheck>>>
}