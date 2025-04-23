package com.example.worktimetracker.data.remote.request

import java.time.LocalDateTime


data class CreateLogRequest(
    val shiftId: Int,
    val userId: Int,
    val checkTime: LocalDateTime,
    val type: Int,
    val reason: String
)
