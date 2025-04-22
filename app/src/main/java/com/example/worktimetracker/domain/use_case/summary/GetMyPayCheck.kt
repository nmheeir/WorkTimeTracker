package com.example.worktimetracker.domain.use_case.summary

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.data.remote.repo.SummaryRepository
import com.skydoves.sandwich.ApiResponse

class GetMyPayCheck(
    private val summaryRepository: SummaryRepository
) {
    suspend operator fun invoke(token: String): ApiResponse<DataResponse<List<PayCheck>>> {
        return summaryRepository.getMyPayCheck(token)
    }
}