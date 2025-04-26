package com.example.worktimetracker.data.remote.response

import com.example.worktimetracker.data.remote.enums.ProjectStatus
import java.time.LocalDateTime

data class Project(
    val createdAt: LocalDateTime,
    val description: String,
    val endDate: LocalDateTime?,
    val id: Int,
    val managerId: Int,
    val name: String,
    val startDate: LocalDateTime,
    val status: ProjectStatus,
    val updatedAt: LocalDateTime,
    val statistics: TaskStatistics,
)