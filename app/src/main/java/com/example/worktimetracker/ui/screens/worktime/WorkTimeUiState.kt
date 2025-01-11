package com.example.worktimetracker.ui.screens.worktime

import com.example.worktimetracker.data.remote.response.AttendanceRecord
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.data.remote.response.TotalWorkTime
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

data class WorkTimeUiState(
    val month: LocalDate = LocalDate.now(),
    val chartData: List<DayWorkTime> = emptyList(),
    val totalWorkTime: TotalWorkTime = TotalWorkTime(0,"",0.0,0.0,0.0,0.0),
    val attendanceRecord: AttendanceRecord = AttendanceRecord(0,0,0,0, "",""),
    val startTime: String = month.withDayOfMonth(1).atStartOfDay().toString(),
    val endTime: String = month.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59).toString(),

    val isChartDataLoading : Boolean = false
)