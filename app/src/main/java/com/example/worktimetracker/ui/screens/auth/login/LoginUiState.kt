package com.example.worktimetracker.ui.screens.auth.login

data class LoginUiState(
    val isLoading: Boolean = false,

    val username: String = "",
    val usernameError: String? = null,

    val password: String = "",
    val passwordError: String? = null,
)