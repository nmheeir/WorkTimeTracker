package com.example.worktimetracker.ui.screens.log

import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.data.remote.response.exampleLogs

data class LogUiState(
    val isLoading: Boolean = false,
    val log: Log? = null,
    val listLog: List<Log> = exampleLogs
)
