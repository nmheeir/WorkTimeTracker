package com.example.worktimetracker.data.remote.repoImpl

import android.util.Log
import com.example.worktimetracker.data.remote.api.AuthApi
import com.example.worktimetracker.data.remote.request.UserLoginRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.domain.repository.remote.AuthRepository
import com.example.worktimetracker.domain.result.ApiResult

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun login(username: String, password: String): ApiResult<DataResponse<Token>> {
        return try {
            Log.d("login", username + password)

            val response = authApi.login(UserLoginRequest(username, password))
            Log.d("login", response.toString())

            when (response.code()) {
                200 -> {
                    ApiResult.Success(response.body())
                }

                else -> ApiResult.Error("Sign in error: " + response.message())
            }
        } catch (e: Exception) {
            ApiResult.Error("Sign in error - lỗi local: " + e.message)
        }
    }
}