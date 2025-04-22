package com.example.worktimetracker.domain.use_case.log

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.LogModel
import com.example.worktimetracker.data.remote.repo.LogRepository
import com.skydoves.sandwich.ApiResponse

class GetLogs(
    private val logRepository: LogRepository
) {
    suspend operator fun invoke(token: String): ApiResponse<DataResponse<List<LogModel>>> {
        return logRepository.getLogs(token)
    }
}