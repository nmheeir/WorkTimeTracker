package com.example.worktimetracker.ui.screens.check

sealed interface CheckUiEvent {
    data object Success : CheckUiEvent
    data class Failure(val message: String) : CheckUiEvent
}

sealed interface CheckUiAction {
    data object CheckIn : CheckUiAction
    data object CheckOut : CheckUiAction
}


