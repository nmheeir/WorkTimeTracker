package com.example.worktimetracker.domain.use_case.summary

data class SummaryUseCase(
    val getWorkTimeEachDay: GetWorkTimeEachDay,
    val getMyPayCheck: GetMyPayCheck,
    val getTotalWorkTime: GetTotalWorkTime,
    val getAttendanceRecord: GetAttendanceRecord
)
