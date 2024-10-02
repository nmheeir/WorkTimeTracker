package com.example.worktimetracker.ui.screens.activity

sealed class ActivityUiEvent {
    data object GetChecks : ActivityUiEvent()
    data class OnFromDateChange(val value: java.time.LocalDate?) : ActivityUiEvent()
    data class OnToDateChange(val value: java.time.LocalDate?) : ActivityUiEvent()
}