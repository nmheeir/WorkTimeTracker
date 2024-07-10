package com.example.worktimetracker.ui.screens.auth.login

sealed class LoginUiEvent {
    data class UsernameChange(val value: String) : LoginUiEvent()
    data class PasswordChange(val value: String) : LoginUiEvent()
    data object Login : LoginUiEvent()
}