package com.example.worktimetracker.ui.screens.auth.forgotpw.screen

sealed class CreateNewPasswordUiEvent {
    data object ResetPasswordSuccess : CreateNewPasswordUiEvent()
    data class ResetPasswordFailure(val msg: String) : CreateNewPasswordUiEvent()
}