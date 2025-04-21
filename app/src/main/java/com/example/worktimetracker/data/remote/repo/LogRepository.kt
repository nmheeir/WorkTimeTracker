package com.example.worktimetracker.data.remote.repo

import com.example.worktimetracker.data.remote.request.CreateLogRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Log
import com.skydoves.sandwich.ApiResponse

interface LogRepository {
    suspend fun createLog(log: CreateLogRequest, token: String): ApiResponse<DataResponse<Log>>
    suspend fun getLogs(token: String): ApiResponse<DataResponse<List<Log>>>
}