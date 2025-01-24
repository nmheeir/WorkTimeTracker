package com.example.worktimetracker.domain.use_case.auth

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.domain.repository.AuthRepository
import com.skydoves.sandwich.ApiResponse

class ResetPassword(
    private val iAuthRepo: AuthRepository
) {
    suspend operator fun invoke(
        token: String,
        newPassword: String
    ): ApiResponse<DataResponse<Unit>> {
        return iAuthRepo.resetPassword(token, newPassword)
    }
}

class RequestPasswordReset(
    private val iAuthRepo: AuthRepository
) {
    suspend operator fun invoke(
        email: String
    ): ApiResponse<DataResponse<Unit>> {
        return iAuthRepo.requestPasswordReset(email)
    }
}