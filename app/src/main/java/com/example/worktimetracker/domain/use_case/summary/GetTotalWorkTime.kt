package com.example.worktimetracker.domain.use_case.summary

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.TotalWorkTime
import com.example.worktimetracker.data.remote.repo.SummaryRepository
import com.skydoves.sandwich.ApiResponse

class GetTotalWorkTime (
    private val summaryRepository: SummaryRepository
) {
    suspend operator fun invoke(
        token: String,
        start: String,
        end: String
    ): ApiResponse<DataResponse<TotalWorkTime>> {
        return summaryRepository.getMyTotalWorkTime(token, start, end)
    }
}