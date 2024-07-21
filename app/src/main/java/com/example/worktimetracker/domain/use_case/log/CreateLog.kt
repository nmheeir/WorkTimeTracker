package com.example.worktimetracker.domain.use_case.log

import com.example.worktimetracker.data.remote.request.CreateLogRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.domain.repository.LogRepository
import com.example.worktimetracker.domain.result.ApiResult

class CreateLog(
    private val logRepository: LogRepository
) {
    suspend operator fun invoke(
        log: CreateLogRequest,
        token: String
    ): ApiResult<DataResponse<Log>> {
        return logRepository.createLog(log, token)
    }
}