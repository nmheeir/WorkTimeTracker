package com.example.worktimetracker.ui.screens.log

import com.example.worktimetracker.data.remote.response.LogModel
import com.example.worktimetracker.data.remote.response.LogType
import java.time.LocalDate
import java.time.LocalTime

data class LogUiState(
    val isLoading: Boolean = true,
    val type: LogType = LogType.CHECK_IN,
    val time: LocalTime = LocalTime.now(),
    val date: LocalDate = LocalDate.now(),
    val reason : String = "",
    val listLog: List<LogModel> = emptyList(),
)
