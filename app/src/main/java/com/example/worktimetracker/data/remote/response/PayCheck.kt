package com.example.worktimetracker.data.remote.response

import java.time.LocalDateTime

data class PayCheck(
    val allowanced: Double,
    val createAt: String,
    val end: LocalDateTime,
    val id: Int,
    val nightWork: Double,
    val normalWork: Double,
    val overtimeWork: Double,
    val start: LocalDateTime,
    val totalIncome: Double,
    val totalWorkTime: Double,
    val userId: Int
)