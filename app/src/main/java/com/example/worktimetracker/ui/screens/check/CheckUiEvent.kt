package com.example.worktimetracker.ui.screens.check

sealed class CheckUiEvent {
    data object CheckIn : CheckUiEvent()
    data object CheckOut : CheckUiEvent()
}
