package com.example.worktimetracker.ui.screens.sharedViewModel

sealed class SharedUiEvent {
    data object GetUserInfo : SharedUiEvent()
    data object Logout: SharedUiEvent()
    data object UpdateUser: SharedUiEvent()
    data class OnUpdatePasswordChange(val password: String) : SharedUiEvent()
    data class OnUpdateEmailChange(val email: String) : SharedUiEvent()
    data class OnUpdateAddressChange(val address: String) : SharedUiEvent()
}