package com.example.worktimetracker.domain.use_case.check

import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.domain.repository.CheckRepository
import com.example.worktimetracker.domain.result.ApiResult

class GetCheckWithDate(
    private val checkRepository: CheckRepository
) {
    suspend operator fun invoke(
        token : String,
        start : Long? = null,
        end : Long? = null,
    ): ApiResult<DataResponse<List<Check>>> {
        return checkRepository.getCheckWithDate(token, start, end)
    }
}