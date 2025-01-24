package com.example.worktimetracker.data.remote.repoImpl

import com.example.worktimetracker.data.remote.api.AuthApi
import com.example.worktimetracker.data.remote.request.UserLoginRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.domain.repository.AuthRepository
import com.skydoves.sandwich.ApiResponse

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun login(
        username: String,
        password: String,
        deviceToken: String
    ): ApiResponse<DataResponse<Token>> {
        return authApi.login(UserLoginRequest(username, password, deviceToken))
    }

    override suspend fun requestPasswordReset(
        email: String
    ): ApiResponse<DataResponse<Unit>> {
        return authApi.requestPasswordReset(email)
    }

    override suspend fun resetPassword(
        token: String,
        newPassword: String
    ): ApiResponse<DataResponse<Unit>> {
        return authApi.resetPassword(token, newPassword)
    }
}