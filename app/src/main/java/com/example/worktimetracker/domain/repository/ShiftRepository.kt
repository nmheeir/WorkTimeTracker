package com.example.worktimetracker.domain.repository

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.domain.result.ApiResult
import com.skydoves.sandwich.ApiResponse
import java.time.LocalDateTime

interface ShiftRepository {
    suspend fun getMyShift(
        start: Long? = null,
        end: Long? = null,
        token: String
    ) : ApiResponse<DataResponse<List<Shift>>>

    suspend fun getMyShiftsInMonth(
        month: Int? = null,
        year: Int? = null,
        token: String
    ) : ApiResponse<DataResponse<List<Shift>>>
}