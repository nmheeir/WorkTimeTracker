package com.example.worktimetracker.ui.screens.worktime

import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.helper.Helper

data class WorkTimeUiState(
    val chartData: List<DayWorkTime> = emptyList(),
    val startTime: Long = 1722474000000,
    val endTime: Long = 1722790800000
)