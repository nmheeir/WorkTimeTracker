package com.example.worktimetracker.data.remote.repo.impl

import com.example.worktimetracker.data.remote.api.LogApi
import com.example.worktimetracker.data.remote.request.CreateLogRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.LogModel
import com.example.worktimetracker.data.remote.repo.LogRepository
import com.skydoves.sandwich.ApiResponse

class LogRepositoryImpl(
    private val logApi: LogApi
) : LogRepository {
    override suspend fun createLog(
        log: CreateLogRequest,
        token: String
    ): ApiResponse<DataResponse<LogModel>> {
        return logApi.createLog(log, "Bearer $token")
    }

    override suspend fun getLogs(
        token: String
    ): ApiResponse<DataResponse<List<LogModel>>> {
        return logApi.getLogs("Bearer $token")
    }
}