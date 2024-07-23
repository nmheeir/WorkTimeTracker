package com.example.worktimetracker.ui.screens.log

import androidx.datastore.preferences.protobuf.Empty
import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.data.remote.response.LogType
import com.example.worktimetracker.data.remote.response.exampleLogs

data class LogUiState(
    val isLoading: Boolean = false,
    val type: LogType = LogType.CHECK_IN,
    val time: String = "",
    val date: String = "",
    val createAt : String = "",
    val listLog: List<Log> = emptyList(),
)
