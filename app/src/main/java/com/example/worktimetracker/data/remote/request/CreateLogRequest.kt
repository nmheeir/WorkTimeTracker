package com.example.worktimetracker.data.remote.request

import com.example.worktimetracker.data.remote.response.LogType

data class CreateLogRequest(
    val userId: Int,
    val reason: String,
    val type: LogType,
    val createAt: String
)
