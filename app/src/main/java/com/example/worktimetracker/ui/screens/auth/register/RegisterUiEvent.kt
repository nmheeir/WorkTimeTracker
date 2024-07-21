package com.example.worktimetracker.ui.screens.auth.register

import com.example.worktimetracker.ui.screens.auth.login.LoginUiEvent

sealed class RegisterUiEvent {
    data class UsernameChange(val value: String) : RegisterUiEvent()
    data class PasswordChange(val value: String) : RegisterUiEvent()
    data class EmailChange(val value: String) : RegisterUiEvent()
    data class PasswordConfirmChange(val value: String) : RegisterUiEvent()
    data object Register : RegisterUiEvent()
}
