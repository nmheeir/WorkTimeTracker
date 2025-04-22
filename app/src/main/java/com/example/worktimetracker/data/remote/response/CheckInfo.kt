package com.example.worktimetracker.data.remote.response

import com.example.worktimetracker.data.remote.enums.CheckType
import java.time.LocalDateTime

data class UserActivityDto(
    val user: User,
    val checkInfos: List<CheckInfo>
)

data class CheckInfo(
    val type: CheckType,
    val time: LocalDateTime,
    val status: String
)
