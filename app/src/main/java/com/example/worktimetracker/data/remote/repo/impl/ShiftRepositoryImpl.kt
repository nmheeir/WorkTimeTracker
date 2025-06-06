package com.example.worktimetracker.data.remote.repo.impl

import com.example.worktimetracker.data.remote.api.ShiftApi
import com.example.worktimetracker.data.remote.response.PagedDataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.data.remote.repo.ShiftRepository
import com.skydoves.sandwich.ApiResponse
import java.time.LocalDateTime

class ShiftRepositoryImpl(
    private val shiftApi: ShiftApi
) : ShiftRepository {
    override suspend fun getMyShift(
        start: LocalDateTime?,
        end: LocalDateTime?,
        token: String
    ): ApiResponse<PagedDataResponse<List<Shift>>> {
        return shiftApi.getMyShift(start, end, "Bearer $token")
    }

    override suspend fun getMyShiftsByDate(
        day: Int?,
        month: Int?,
        year: Int?,
        token: String,
        includeCheckRecord: Boolean
    ): ApiResponse<PagedDataResponse<List<Shift>>> {
        return shiftApi.getMyShiftsByDate(day, month, year, includeCheckRecord, "Bearer $token")
    }

}