package com.example.worktimetracker.ui.screens.log

sealed class LogUiEvent() {
    data object CreateLog : LogUiEvent()
    data object GetLogs : LogUiEvent()
}
