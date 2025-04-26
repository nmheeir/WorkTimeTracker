package com.example.worktimetracker.data.remote.response

import java.time.LocalDateTime

data class ReportInformation(
    val id: Int,
    val title: String,
    val description: String,
    val reportUrl: String?,
    val taskId: Int,
    val author: UserProfileDto,
    val createdAt: LocalDateTime,
)
