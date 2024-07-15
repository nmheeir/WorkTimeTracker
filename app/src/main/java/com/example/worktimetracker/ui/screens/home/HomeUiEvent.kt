package com.example.worktimetracker.ui.screens.home

sealed class HomeUiEvent {
    data object GetUserInfo : HomeUiEvent()

}