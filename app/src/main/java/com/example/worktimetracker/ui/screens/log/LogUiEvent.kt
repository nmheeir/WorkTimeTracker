package com.example.worktimetracker.ui.screens.log

import com.example.worktimetracker.data.remote.response.LogType
import java.time.LocalDate
import java.time.LocalTime

sealed class LogUiEvent() {
    data object CreateLog : LogUiEvent()
    data object GetLogs : LogUiEvent()
    data class OnLogTypeChange(val value: LogType) : LogUiEvent()
    data class OnTimeChange(val value: LocalTime) : LogUiEvent()
    data class OnDateChange(val value: LocalDate) : LogUiEvent()
}
