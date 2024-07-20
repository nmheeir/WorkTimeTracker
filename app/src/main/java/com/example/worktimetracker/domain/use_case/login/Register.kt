package com.example.worktimetracker.domain.use_case.login

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.domain.repository.AuthRepository
import com.example.worktimetracker.domain.result.ApiResult

class Register(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        username: String,
        password: String,
        email: String
    ): ApiResult<DataResponse<Token>> {
        return authRepository.register(username, password, email)
    }
}