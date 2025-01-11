package com.example.worktimetracker.domain.repository

import androidx.datastore.preferences.protobuf.Api
import com.example.worktimetracker.data.remote.response.AttendanceRecord
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.data.remote.response.TotalWorkTime
import com.example.worktimetracker.domain.result.ApiResult
import com.skydoves.sandwich.ApiResponse

interface SummaryRepository {
    suspend fun getMyWorkTimeEachDay(
        token: String,
        start: String?,
        end: String?
    ) : ApiResponse<DataResponse<List<DayWorkTime>>>

    suspend fun getMyPayCheck(
        token: String
    ) : ApiResponse<DataResponse<List<PayCheck>>>

    suspend fun getMyTotalWorkTime(
        token: String,
        start: String?,
        end: String?
    ) : ApiResponse<DataResponse<TotalWorkTime>>

    suspend fun getAttendanceRecord (
        token: String,
        start: String?,
        end: String?,
    ) : ApiResponse<DataResponse<AttendanceRecord>>
}