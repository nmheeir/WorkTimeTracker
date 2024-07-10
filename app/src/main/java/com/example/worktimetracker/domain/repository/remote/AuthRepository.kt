package com.example.worktimetracker.domain.repository.remote

import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.domain.result.ApiResult
import retrofit2.http.POST

interface AuthRepository {
    suspend fun login(
        username: String,
        password: String
    ) : ApiResult<User>
}