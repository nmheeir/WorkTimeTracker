package com.example.worktimetracker.domain.repository

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.domain.result.ApiResult

interface WorkTimeRepository {
    suspend fun getMyWorkTimeEachDay(
        token: String,
        start: Long,
        end: Long
    ) : ApiResult<DataResponse<List<DayWorkTime>>>
}