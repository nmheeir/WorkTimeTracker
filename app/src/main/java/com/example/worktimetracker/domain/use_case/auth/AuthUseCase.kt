package com.example.worktimetracker.domain.use_case.auth

data class AuthUseCase(
    val login: Login,
    val resetPassword: ResetPassword,
    val requestPasswordReset: RequestPasswordReset,
)