package com.example.worktimetracker.ui.screens.salary


sealed interface SalaryUiEvent {
    data object Success : SalaryUiEvent
    data class Failure(val message: String) : SalaryUiEvent
}