package com.example.worktimetracker.ui.screens.worktime

sealed interface WorkTimeUiEvent {
    data object Success : WorkTimeUiEvent
    data class Failure(val message: String) : WorkTimeUiEvent
}

sealed interface WorkTimeUiAction {
    data class OnMonthChange(val value: Int) : WorkTimeUiAction
}