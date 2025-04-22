package com.example.worktimetracker.data.remote.repo

import com.example.worktimetracker.data.remote.response.PagedDataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.skydoves.sandwich.ApiResponse
import java.time.LocalDateTime

interface ShiftRepository {
    suspend fun getMyShift(
        start: LocalDateTime? = null,
        end: LocalDateTime? = null,
        token: String
    ) : ApiResponse<PagedDataResponse<List<Shift>>>

    suspend fun getMyShiftsByDate(
        day: Int? = null,
        month: Int? = null,
        year: Int? = null,
        token: String,
        includeCheckRecord: Boolean = false
    ) : ApiResponse<PagedDataResponse<List<Shift>>>
}