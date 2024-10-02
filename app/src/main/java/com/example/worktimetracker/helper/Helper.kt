package com.example.worktimetracker.helper

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Helper {
    companion object {
        fun isValidEmail(email: String): Boolean {
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
            return emailRegex.matches(email)
        }
        fun isValidPassword(password: String): Boolean {
            val passwordRegex = "^(?=.*[0-9])(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$".toRegex()
            return passwordRegex.matches(password)
        }

        fun convertMillisToDate(millis: Long): String {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = Date(millis)
            return dateFormat.format(date)
        }

        fun convertMillisToTimeStamp(millis: Long): String {
            val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = Date(millis)
            return dateFormat.format(date)
        }

        fun localDateToEpochMillisStart(localDate: LocalDate?): Long? {
            return localDate?.atStartOfDay()?.toInstant(ZoneOffset.UTC)?.toEpochMilli()
        }

        fun localDateToEpochMillisEnd(localDate: LocalDate?): Long? {
            return localDate?.let {
                LocalDateTime.of(it, LocalTime.MAX).toInstant(ZoneOffset.UTC).toEpochMilli()
            }
        }


        fun Long.formatMillisToDate(): String {
            // Tạo một đối tượng Date từ mili giây
            val date = Date(this)

            // Định dạng ngày tháng năm theo định dạng DD-MM-YY
            val dateFormat = SimpleDateFormat("dd-MM-yy")

            // Trả về chuỗi định dạng của ngày
            return dateFormat.format(date)
        }
    }
}