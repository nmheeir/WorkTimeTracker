package com.example.worktimetracker.domain.use_case.shift

data class ShiftUseCase (
    var getMyShift : GetMyShift,
    var getShiftsByDate: GetMyShiftsByDate
)