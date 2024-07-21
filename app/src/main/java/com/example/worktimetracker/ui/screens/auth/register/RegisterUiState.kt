package com.example.worktimetracker.ui.screens.auth.register

import java.lang.Error

data class RegisterUiState(
    val isLoading: Boolean = false,

    val username: String = "",
    val usernameError: String? = null,

    val email: String = "",
    val emailError: String? = null,

    val password: String = "",
    val passwordError: String? = null,

    val passwordConfirm: String = "",
    val passwordConfirmError: String? = null,

)
