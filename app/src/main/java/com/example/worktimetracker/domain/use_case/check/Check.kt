package com.example.worktimetracker.domain.use_case.check

import com.example.worktimetracker.data.remote.request.CheckRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.domain.repository.AuthRepository
import com.example.worktimetracker.domain.repository.CheckRepository
import com.example.worktimetracker.domain.result.ApiResult
import com.skydoves.sandwich.ApiResponse
import java.util.Objects

class Check(
    private val checkRepository: CheckRepository
) {
    suspend operator fun invoke(
        checkRequest: CheckRequest,
        token: String
    ): ApiResponse<DataResponse<Any>> {
        return checkRepository.check(checkRequest, token)
    }
}