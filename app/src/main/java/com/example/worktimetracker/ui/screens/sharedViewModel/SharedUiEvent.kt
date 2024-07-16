package com.example.worktimetracker.ui.screens.sharedViewModel

sealed class SharedUiEvent {
    data object GetUserInfo : SharedUiEvent()
    data object Logout: SharedUiEvent()
}