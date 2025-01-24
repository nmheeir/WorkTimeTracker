package com.example.worktimetracker.ui.screens.auth.forgotpw

data class ForgotPasswordUiState(
    val token: String = "",

    val email: String = "",
    val isLoading: Boolean = false,

    val newPassword: String = "",
    val confirmPassword: String = "",

    val isError: Boolean = false,
    val error: String = "",

    val isButtonEnabled: Boolean = false
)
