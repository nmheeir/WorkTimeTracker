package com.example.worktimetracker.ui.screens.sharedViewModel

sealed class SharedUiEvent {
    data object GetUserInfo : SharedUiEvent()
    data object Logout: SharedUiEvent()
    data class UploadImage(val avatarUrl: String) : SharedUiEvent()
}