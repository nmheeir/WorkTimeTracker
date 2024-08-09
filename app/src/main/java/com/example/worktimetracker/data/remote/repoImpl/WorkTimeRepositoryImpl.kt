package com.example.worktimetracker.data.remote.repoImpl

import com.example.worktimetracker.data.remote.api.SummaryApi
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.domain.repository.WorkTimeRepository
import com.example.worktimetracker.domain.result.ApiResult

class WorkTimeRepositoryImpl(
    private val summaryApi: SummaryApi
) : WorkTimeRepository {
    override suspend fun getMyWorkTimeEachDay(
        token: String,
        start: Long,
        end: Long
    ): ApiResult<DataResponse<List<DayWorkTime>>> {
        return try {
            val response = summaryApi.getMyWorkTimeEachDay("Bearer $token", start, end)

            when (response.code()) {
                200 -> {
                    ApiResult.Success(response.body()!!)
                }

                else -> {
                    val errorResponse = DataResponse<List<DayWorkTime>>(
                        _data = null,
                        _message = response.message(),
                        _success = false
                    )
                    ApiResult.Error(errorResponse)
                }
            }
        } catch (e: Exception) {
            ApiResult.NetworkError(e.message!!)
        }
    }
}