package com.example.worktimetracker.data.remote.response


data class CheckRecord (
    val attendanceType: Int,
    val workTime: Double
)

enum class AttendanceType {
    Full,
    Partial,
    Absent
}


