package com.example.worktimetracker.ui.screens.auth.login

data class LoginUiState(
    val isLoading: Boolean = false,

    val username: String = "",
    val isUsernameEmpty: Boolean = false,

    val password: String = "",
    val isPasswordEmpty: Boolean = false,

    val error: String = "",
    val isError: Boolean = false,

    val rememberLogin: Boolean = false
)




