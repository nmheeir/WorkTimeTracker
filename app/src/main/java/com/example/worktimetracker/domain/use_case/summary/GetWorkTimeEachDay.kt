package com.example.worktimetracker.domain.use_case.summary

import android.util.Log
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.domain.repository.SummaryRepository
import com.skydoves.sandwich.ApiResponse

class GetWorkTimeEachDay (
    private val summaryRepository: SummaryRepository
) {
    suspend operator fun invoke(
        token: String,
        start: String,
        end: String
    ): ApiResponse<DataResponse<List<DayWorkTime>>> {
        val apirs = summaryRepository.getMyWorkTimeEachDay(token, start, end)
        Log.d("ShiftTest", apirs.toString())
        return apirs
    }
}