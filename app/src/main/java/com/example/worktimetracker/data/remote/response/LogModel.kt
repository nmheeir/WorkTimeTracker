package com.example.worktimetracker.data.remote.response

import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.enums.CheckType
import java.time.LocalDateTime

data class LogModel(
    val id: Int,
    val userId: Int,
    val type: CheckType,
    val status: LogStatus,
    val reason: String,
    val createAt: LocalDateTime,
    val checkTime: LocalDateTime,
    val approvalTime: LocalDateTime?,
    val user: User,
)

enum class LogStatus {
    PENDING,
    APPROVED,
    REJECTED;

    fun displayStatus(): String {
        return this.name.lowercase().replaceFirstChar {
            it.uppercase()
        }
    }

    companion object {
        fun namesToList(): List<String> {
            return entries.map {
                it.name.lowercase().replaceFirstChar { firstChar ->
                    firstChar.uppercase()
                }
            }
        }
    }

    fun statusColor(): Int {
        return when (this) {
            PENDING -> R.color.teal
            APPROVED -> R.color.blue
            REJECTED -> R.color.red
        }
    }
}

enum class LogType {
    CHECK_IN,
    CHECK_OUT;

    companion object {
        fun namesToList(): List<String> {
            return entries.map {
                it.name.lowercase().replace('_', ' ')
                    .replaceFirstChar { firstChar ->
                        firstChar.uppercase()
                    }
            }
        }
    }

    fun displayType(): String {
        return this.name.lowercase().replace('_', ' ').replaceFirstChar { it.uppercase() }
    }
}