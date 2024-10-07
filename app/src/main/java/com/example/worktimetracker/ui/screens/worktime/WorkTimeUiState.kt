package com.example.worktimetracker.ui.screens.worktime

import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.helper.Helper

data class WorkTimeUiState(
    val chartData: List<DayWorkTime> = emptyList(),
    val totalWorkTime: Long = 0,
    val startTime: Long = 1725127200000,
    val endTime: Long = 1727632800000
)