package com.example.worktimetracker.domain.use_case.summary

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.domain.repository.SummaryRepository
import com.example.worktimetracker.domain.result.ApiResult

class GetMyPayCheck(
    private val summaryRepository: SummaryRepository
) {
    suspend operator fun invoke(token: String): ApiResult<DataResponse<List<PayCheck>>> {
        return summaryRepository.getMyPayCheck(token)
    }
}