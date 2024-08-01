package com.example.worktimetracker.helper

import java.text.SimpleDateFormat
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

        fun getStartOfDayInMillis(): Long {
            val calendar = Calendar.getInstance()
            // Set the calendar time to the start of the day
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.timeInMillis
        }

        fun Long.convertLongToWorkTime(): Int {
            return (this / 3600000).toInt()
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