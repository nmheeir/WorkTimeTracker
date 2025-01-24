package com.example.worktimetracker.data.remote.response

import com.example.worktimetracker.R

data class Log(
    val id: Int,
    val userId: Int,
    val type: Int,
    val status: Int,
    val createAt: String,
    val checkTime: String,
    val user: User? = null
) {
    fun statusName(): String {
        return when (status) {
            0 -> LogStatus.PENDING.displayStatus()
            1 -> LogStatus.APPROVED.displayStatus()
            2 -> LogStatus.REJECTED.displayStatus()
            else -> "UNKNOWN"
        }
    }

    fun typeName(): String {
        return when (type) {
            0 -> LogType.CHECK_IN.displayType()
            1 -> LogType.CHECK_OUT.displayType()
            else -> "UNKNOWN"
        }
    }

    fun statusColor(): Int {
        return when (status) {
            0 -> LogStatus.PENDING.statusColor()
            1 -> LogStatus.APPROVED.statusColor()
            2 -> LogStatus.REJECTED.statusColor()
            else -> R.color.black
        }
    }
}

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
                    .replaceFirstChar {firstChar ->
                        firstChar.uppercase()
                    }
            }
        }
    }

    fun displayType(): String {
        return this.name.lowercase().replace('_', ' ').replaceFirstChar { it.uppercase() }
    }
}

val listLogType = LogType.namesToList()
val listLogStatus = LogStatus.namesToList()

//Cái này là để test
val exampleLogs = listOf(
    Log(
        checkTime = "2022-01-01 00:00:00",
        createAt = "2022-01-01 00:00:00",
        id = 1,
        status = 0,
        type = 1,
        userId = 1
    ),
    Log(
        checkTime = "2022-01-01 00:00:00",
        createAt = "2022-01-01 00:00:00",
        id = 1,
        status = 0,
        type = 1,
        userId = 1
    ),
    Log(
        checkTime = "2022-01-01 00:00:00",
        createAt = "2022-01-01 00:00:00",
        id = 1,
        status = 0,
        type = 1,
        userId = 1
    ),
    Log(
        checkTime = "2022-01-01 00:00:00",
        createAt = "2022-01-01 00:00:00",
        id = 1,
        status = 0,
        type = 1,
        userId = 1
    ),
    Log(
        checkTime = "2022-01-01 00:00:00",
        createAt = "2022-01-01 00:00:00",
        id = 1,
        status = 2,
        type = 1,
        userId = 1
    ),
    Log(
        checkTime = "2022-01-01 00:00:00",
        createAt = "2022-01-01 00:00:00",
        id = 1,
        status = 1,
        type = 1,
        userId = 1
    ),
)