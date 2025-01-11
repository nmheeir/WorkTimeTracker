package com.example.worktimetracker.helper

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.Locale

class ISOFormater {
    companion object {
        fun formatDateTimeToDate1(input: String?): String {
            try {
                // Định dạng đầu vào
                val inputFormatter = DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .optionalStart()
                    .appendFraction(ChronoField.MILLI_OF_SECOND, 0, 9, true) // Linh hoạt từ 0 đến 9 chữ số
                    .optionalEnd()
                    .toFormatter(Locale.ENGLISH)
                // Định dạng đầu ra
                val outputFormatter = DateTimeFormatter.ofPattern("dd, MMM yyyy", Locale.ENGLISH)

                // Chuyển đổi chuỗi thành LocalDateTime
                val dateTime = LocalDateTime.parse(input, inputFormatter)

                // Định dạng lại thành chuỗi theo yêu cầu
                return dateTime.format(outputFormatter)
            }
            catch (e:Exception) {
                return "--, None ----"
            }
        }

        fun LocalDateFormatDate1(localDate: LocalDate) : String {
            val formatter = DateTimeFormatter.ofPattern(("d, MMM yyyy"))
            return localDate.format(formatter)
        }

        fun formatDateTimeToTime(input: String?): String {
            try {
                // Định dạng đầu vào
                val inputFormatter = DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .optionalStart()
                    .appendFraction(ChronoField.MILLI_OF_SECOND, 0, 9, true) // Linh hoạt từ 0 đến 9 chữ số
                    .optionalEnd()
                    .toFormatter(Locale.ENGLISH)
                // Định dạng đầu ra cho giờ và phút
                val outputFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)

                // Chuyển đổi chuỗi thành LocalDateTime
                val dateTime = LocalDateTime.parse(input, inputFormatter)

                // Định dạng lại thành chuỗi giờ và phút
                return dateTime.format(outputFormatter)
            }
            catch (e: Exception) {
                return "--:--"
            }
        }

        fun fromISODateTimetoLocalDateTime(input: String?) : LocalDateTime {
            return try {
                val inputFormatter = DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .optionalStart()
                    .appendFraction(ChronoField.MILLI_OF_SECOND, 0, 9, true) // Linh hoạt từ 0 đến 9 chữ số
                    .optionalEnd()
                    .toFormatter(Locale.ENGLISH)
                LocalDateTime.parse(input, inputFormatter)
            }
            catch (e: Exception) {
                e.printStackTrace()
                LocalDateTime.MIN
            }
        }
    }


}