package com.example.worktimetracker.ui.screens.log

import com.example.worktimetracker.data.remote.response.LogType
import java.time.LocalDate
import java.time.LocalTime

sealed interface LogUiAction {
    data object CreateLog : LogUiAction
    data object GetLogs : LogUiAction
    data class OnLogTypeChange(val value: LogType) : LogUiAction
    data class OnTimeChange(val value: LocalTime) : LogUiAction
    data class OnDateChange(val value: LocalDate) : LogUiAction
    data class OnReasonChange(val value: String) : LogUiAction
}

sealed interface LogUiEvent {
    data object Success : LogUiEvent
    data class Failure(val message: String) : LogUiEvent
    data object CreateLogSuccess: LogUiEvent
}