package com.example.worktimetracker.data.remote.response

import java.time.LocalDateTime

data class Report(
    val id: Int,
    val title: String,
    val description: String,
    val taskId: Int,
    val author: UserProfileDto,
    val reportUrl: String,
    val createdAt: LocalDateTime,
)