package com.example.worktimetracker.domain.use_case.summary

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.domain.repository.SummaryRepository
import com.example.worktimetracker.domain.result.ApiResult
import com.skydoves.sandwich.ApiResponse

class GetTotalWorkTime (
    private val summaryRepository: SummaryRepository
) {
    suspend operator fun invoke(
        token: String,
        start: Long,
        end: Long
    ): ApiResponse<DataResponse<Long>> {
        return summaryRepository.getMyTotalWorkTime(token, start, end)
    }
}