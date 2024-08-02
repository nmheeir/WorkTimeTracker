package com.example.worktimetracker.ui.screens.auth.login

data class LoginUiState(
    val isLoading: Boolean = false,

    val username: String = "",
    val isUsernameEmpty: Boolean = true,

    val password: String = "",
    val isPasswordEmpty: Boolean = true,
)