package com.example.worktimetracker.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserSession")
data class UserSession(
    @PrimaryKey val username: String,
    val password: String,
    val avatarUrl: String
)