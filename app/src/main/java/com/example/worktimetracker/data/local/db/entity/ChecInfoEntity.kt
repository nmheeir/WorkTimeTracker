package com.example.worktimetracker.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.worktimetracker.data.remote.enums.CheckType
import java.time.LocalDateTime

@Entity(tableName = "check_infos")
data class CheckInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: CheckType,
    val time: LocalDateTime,
    val status: String
)