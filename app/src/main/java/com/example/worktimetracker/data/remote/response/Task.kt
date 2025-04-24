package com.example.worktimetracker.data.remote.response

import com.example.worktimetracker.data.remote.enums.Priority
import com.example.worktimetracker.data.remote.enums.ProjectStatus
import java.time.LocalDateTime

data class Task(
    val createdAt: LocalDateTime,
    val description: String,
    val dueDate: LocalDateTime,
    val id: Int,
    val name: String,
    val projectId: Int,
    val status: ProjectStatus,
    val priority: Priority,
    val assignees: List<UserProfileDto> = emptyList(),
    val reports: List<Report> = emptyList(),
)