package com.example.worktimetracker.data.remote.repoImpl

import com.example.worktimetracker.data.remote.api.SummaryApi
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.domain.repository.SummaryRepository
import com.example.worktimetracker.domain.result.ApiResult
import com.skydoves.sandwich.ApiResponse

class SummaryRepositoryImpl(
    private val summaryApi: SummaryApi
) : SummaryRepository {
    override suspend fun getMyWorkTimeEachDay(
        token: String,
        start: Long,
        end: Long
    ): ApiResponse<DataResponse<List<DayWorkTime>>> {
        return summaryApi.getMyWorkTimeEachDay(token, start, end)
    }

    override suspend fun getMyPayCheck(
        token: String
    ): ApiResponse<DataResponse<List<PayCheck>>> {
        return summaryApi.getMyPayCheck(token)
    }

    override suspend fun getMyTotalWorkTime(
        token: String,
        start: Long,
        end: Long
    ): ApiResponse<DataResponse<Long>> {
        return summaryApi.getMyTotalWorkTime(token, start, end)
    }
}