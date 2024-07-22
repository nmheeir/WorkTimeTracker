package com.example.worktimetracker.ui.screens.log

import com.example.worktimetracker.data.remote.response.LogType

sealed class LogUiEvent() {
    data object CreateLog : LogUiEvent()
    data object GetLogs : LogUiEvent()
    data class OnLogTypeChange(val value: LogType) : LogUiEvent()
    data class OnTimeChange(val value: String) : LogUiEvent()
    data class OnDateChange(val value: String) : LogUiEvent()
}
