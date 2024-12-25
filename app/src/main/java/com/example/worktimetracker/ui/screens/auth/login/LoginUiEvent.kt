package com.example.worktimetracker.ui.screens.auth.login

sealed interface LoginUiEvent {
    data class UserNotFound(val msg: String) : LoginUiEvent
    data class WrongPassword(val msg: String) : LoginUiEvent

    data object Success : LoginUiEvent
    data class Failure(val msg: String) : LoginUiEvent
}


sealed interface LoginUiAction {
    data class OnUsernameChange(val username: String) : LoginUiAction
    data class OnPasswordChange(val password: String) : LoginUiAction
    data object Login : LoginUiAction
    data class OnRememberLogin(val isRemember: Boolean) : LoginUiAction

    data class UpdateError(val error: String) : LoginUiAction
}