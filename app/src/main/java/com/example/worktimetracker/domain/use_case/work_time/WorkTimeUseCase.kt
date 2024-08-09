package com.example.worktimetracker.domain.use_case.work_time

import com.example.worktimetracker.domain.use_case.summary.GetWorkTimeEachDay

data class WorkTimeUseCase(
    val getWorkTimeEachDay: GetWorkTimeEachDay
)
