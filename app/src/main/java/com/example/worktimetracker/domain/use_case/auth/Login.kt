package com.example.worktimetracker.domain.use_case.auth

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.data.remote.repo.AuthRepository
import com.skydoves.sandwich.ApiResponse

class Login(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        username: String,
        password: String,
        deviceToken: String
    ): ApiResponse<DataResponse<Token>> {
        return authRepository.login(username, password, deviceToken)
    }
}