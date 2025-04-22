package com.example.worktimetracker.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.worktimetracker.data.remote.enums.NotificationType
import java.time.LocalDateTime

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val receivedUsername: String,
    val title: String,
    val description: String,
    val type: NotificationType? = null,
    val isRead: Boolean = false,
    val receivedAt: LocalDateTime,
)
