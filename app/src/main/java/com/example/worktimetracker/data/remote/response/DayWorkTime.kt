package com.example.worktimetracker.data.remote.response

import com.example.worktimetracker.helper.ISOFormater
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class DayWorkTime(
    val date: String,
    val workTime: Double,
    val type: Int
)
{
    val dateTime: LocalDateTime
        get() {
            return ISOFormater.fromISODateTimetoLocalDateTime(date)
        }
}