package com.example.worktimetracker.data.remote.response

data class AttendanceRecord (
    val  id : Int ,
    val  fullAttendance : Int,
    val  partialAttendance : Int ,
    val  absenceAttendance : Int,
    val  start : String,
    val  end : String,
)