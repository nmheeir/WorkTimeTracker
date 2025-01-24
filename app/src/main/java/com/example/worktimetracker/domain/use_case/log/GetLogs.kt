package com.example.worktimetracker.domain.use_case.log

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.domain.repository.LogRepository
import com.skydoves.sandwich.ApiResponse

class GetLogs(
    private val logRepository: LogRepository
) {
    suspend operator fun invoke(token: String): ApiResponse<DataResponse<List<Log>>> {
        return logRepository.getLogs(token)
    }
}