package com.example.worktimetracker.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Helper {
    companion object {

        fun convertMillisToDate(millis: Long): String {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date = Date(millis)
            return dateFormat.format(date)
        }



    }
}
