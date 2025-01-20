package com.example.worktimetracker.data.remote.repoImpl

import com.example.worktimetracker.data.remote.api.CheckApi
import com.example.worktimetracker.data.remote.request.CheckRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.domain.repository.CheckRepository
import com.skydoves.sandwich.ApiResponse
class CheckRepositoryImpl (
    private var checkApi : CheckApi
) : CheckRepository {
    override suspend fun check(checkRequest: CheckRequest, token: String): ApiResponse<DataResponse<Any>> {
        return checkApi.check(checkRequest, "Bearer $token")
    }
}