package com.example.worktimetracker.data.remote.repoImpl

import com.example.worktimetracker.data.remote.api.LogApi
import com.example.worktimetracker.data.remote.request.CreateLogRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.domain.repository.LogRepository
import com.example.worktimetracker.domain.result.ApiResult
import retrofit2.Response

class LogRepositoryImpl(
    private val logApi: LogApi
) : LogRepository {
    override suspend fun createLog(
        log: CreateLogRequest,
        token: String
    ): ApiResult<DataResponse<Log>> {
        return try {
            val response = logApi.createLog(log, token)

            when (response.code()) {
                200 -> {
                    ApiResult.Success(response.body()!!)
                }

                else -> {
                    val errorResponse = DataResponse<Log>(
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

    override suspend fun getLogs(token: String): ApiResult<DataResponse<List<Log>>> {
        return try {
            val bearerToken = "Bearer $token"
            val response: Response<DataResponse<List<Log>>> = logApi.getLogs(bearerToken)

            when (response.code()) {
                200 -> {
                    ApiResult.Success(response.body()!!)
                }

                else -> {
                    val errorResponse = DataResponse<List<Log>>(
                        _data = null,
                        _message = response.message(),
                        _success = false
                    )
                    return ApiResult.Error(errorResponse)
                }
            }
        } catch (e: Exception) {
            ApiResult.NetworkError(e.message!!)
        }
    }
}