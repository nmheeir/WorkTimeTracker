package com.example.worktimetracker.data.local

import androidx.annotation.DrawableRes
import com.example.worktimetracker.R

data class Attendance(
    val title: String,
    @DrawableRes val icon: Int,
    val time: String,
    val desc: String
)

val listAttendance = listOf(
    Attendance(
        title = "Check In",
        icon = R.drawable.ic_google,
        time = "08:00",
        desc = "Check In"
    ),
    Attendance(
        title = "Check Out",
        icon = R.drawable.ic_google,
        time = "17:00",
        desc = "Check Out"
    ),
    Attendance(
        title = "Break Time",
        icon = R.drawable.ic_google,
        time = "12:30",
        desc = "Avg 30 minutes"
    ),
    Attendance(
        title = "Total Days",
        icon = R.drawable.ic_google,
        time = "28",
        desc = "Working day"
    )
)
