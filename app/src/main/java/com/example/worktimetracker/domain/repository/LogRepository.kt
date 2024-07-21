package com.example.worktimetracker.domain.repository

import com.example.worktimetracker.data.remote.request.CreateLogRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.domain.result.ApiResult

interface LogRepository {
    suspend fun createLog(log: CreateLogRequest, token: String): ApiResult<DataResponse<Log>>
    suspend fun getLogs(token: String): ApiResult<DataResponse<List<Log>>>
}