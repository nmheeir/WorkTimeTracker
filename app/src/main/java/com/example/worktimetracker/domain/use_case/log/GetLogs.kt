package com.example.worktimetracker.domain.use_case.log

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.domain.repository.LogRepository
import com.example.worktimetracker.domain.result.ApiResult

class GetLogs(
    private val logRepository: LogRepository
) {
    suspend operator fun invoke(token: String): ApiResult<DataResponse<List<Log>>> {
        return logRepository.getLogs(token)
    }
}