package com.example.worktimetracker.data.remote.repoImpl

import com.example.worktimetracker.data.remote.api.LogApi
import com.example.worktimetracker.data.remote.request.CreateLogRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.domain.repository.LogRepository
import com.skydoves.sandwich.ApiResponse

class LogRepositoryImpl(
    private val logApi: LogApi
) : LogRepository {
    override suspend fun createLog(
        log: CreateLogRequest,
        token: String
    ): ApiResponse<DataResponse<Log>> {
        return logApi.createLog(log, "Bearer $token")
    }

    override suspend fun getLogs(
        token: String
    ): ApiResponse<DataResponse<List<Log>>> {
        return logApi.getLogs("Bearer $token")
    }
}