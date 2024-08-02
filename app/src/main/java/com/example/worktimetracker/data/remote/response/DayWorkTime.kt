package com.example.worktimetracker.data.remote.response

data class DayWorkTime(
    val date: Long,
    val workTime: Long,
    val type: Int
)
