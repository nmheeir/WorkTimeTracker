package com.example.worktimetracker.data.remote.request

data class CreateLogRequest(
    val checkTime: String,
    val type: Int,
    val reason: String
)
