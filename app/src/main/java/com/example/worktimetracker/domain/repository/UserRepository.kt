package com.example.worktimetracker.domain.repository

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.domain.result.ApiResult

interface UserRepository {

    suspend fun getUserByUsername(userName: String): ApiResult<DataResponse<User>>

}