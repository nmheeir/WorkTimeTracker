package com.example.worktimetracker.data.remote.repoImpl

import com.example.worktimetracker.data.remote.api.CheckApi
import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.domain.repository.CheckRepository
import com.example.worktimetracker.domain.result.ApiResult
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response

class CheckRepositoryImpl (
    private var checkApi : CheckApi
) : CheckRepository {
    override suspend fun check(checkType: Int, token: String): ApiResponse<DataResponse<Any>> {
        return checkApi.check(checkType, token)
    }
}