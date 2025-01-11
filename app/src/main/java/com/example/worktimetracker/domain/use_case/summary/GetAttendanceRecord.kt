package com.example.worktimetracker.domain.use_case.summary

import android.util.Log
import com.example.worktimetracker.data.remote.response.AttendanceRecord
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.domain.repository.SummaryRepository
import com.skydoves.sandwich.ApiResponse

class GetAttendanceRecord (
    private val summaryRepository: SummaryRepository
) {
    suspend operator fun invoke(
        token: String,
        start: String? = null,
        end: String? = null
    ): ApiResponse<DataResponse<AttendanceRecord>>{
        return summaryRepository.getAttendanceRecord(token, start, end)
    }
}