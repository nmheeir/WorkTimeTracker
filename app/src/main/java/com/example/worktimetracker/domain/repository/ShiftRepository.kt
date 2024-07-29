package com.example.worktimetracker.domain.repository

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.domain.result.ApiResult
import java.time.LocalDateTime

interface ShiftRepository {
    suspend fun getMyShift(
        start: Long? = null,
        end: Long? = null,
        token: String
    ) : ApiResult<DataResponse<List<Shift>>>
}