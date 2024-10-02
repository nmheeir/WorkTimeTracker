package com.example.worktimetracker.ui.screens.log

import androidx.datastore.preferences.protobuf.Empty
import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.data.remote.response.LogType
import com.example.worktimetracker.data.remote.response.exampleLogs
import java.time.LocalDate
import java.time.LocalTime

data class LogUiState(
    val isLoading: Boolean = false,
    val type: LogType = LogType.CHECK_IN,
    val time: LocalTime = LocalTime.now(),
    val date: LocalDate = LocalDate.now(),
    val createAt : String = "",
    val listLog: List<Log> = emptyList(),
)
