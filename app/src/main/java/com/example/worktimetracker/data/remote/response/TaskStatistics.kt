package com.example.worktimetracker.data.remote.response

data class TaskStatistics(
    val totalTask: Int,
    val completedTask: Int,
    val inProgressTask: Int,
    val onHoldTask: Int,
    val cancelledTask: Int,
    val notStartedTask: Int,
)
