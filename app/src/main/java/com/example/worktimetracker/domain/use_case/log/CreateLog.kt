package com.example.worktimetracker.domain.use_case.log

import com.example.worktimetracker.data.remote.request.CreateLogRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.LogModel
import com.example.worktimetracker.data.remote.repo.LogRepository
import com.skydoves.sandwich.ApiResponse

class CreateLog(
    private val logRepository: LogRepository
) {
    suspend operator fun invoke(
        log: CreateLogRequest,
        token: String
    ): ApiResponse<DataResponse<LogModel>> {
        return logRepository.createLog(log, token)
    }
}