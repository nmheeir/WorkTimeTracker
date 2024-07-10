package com.example.worktimetracker.data.remote.repoImpl

import com.example.worktimetracker.data.remote.api.AuthApi
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.domain.repository.remote.AuthRepository
import com.example.worktimetracker.domain.result.ApiResult

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun login(username: String, password: String): ApiResult<User> {
        return try {
            val response = authApi.login(username, password)
            when (response.code()) {
                200 -> {
                    ApiResult.Success()
                }

                else -> ApiResult.Error("Sign in error: " + response.message())
            }
        } catch (e: Exception) {
            ApiResult.Error("Sign in error: " + e.message)
        }
    }
}