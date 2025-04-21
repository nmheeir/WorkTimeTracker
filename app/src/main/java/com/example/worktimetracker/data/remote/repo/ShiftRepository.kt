package com.example.worktimetracker.data.remote.repo

import com.example.worktimetracker.data.remote.response.PagedDataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.skydoves.sandwich.ApiResponse

interface ShiftRepository {
    suspend fun getMyShift(
        start: String? = null,
        end: String? = null,
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