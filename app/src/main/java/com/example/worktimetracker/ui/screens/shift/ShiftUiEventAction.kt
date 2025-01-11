package com.example.worktimetracker.ui.screens.shift

import java.time.LocalDate
import java.time.YearMonth

sealed interface ShiftUiEvent {
    data object Success : ShiftUiEvent
    data class Failure(val message: String) : ShiftUiEvent
}

sealed interface ShiftUiAction {
    data class DatePick(val date: LocalDate) : ShiftUiAction
    data class MonthChange(val month: YearMonth) : ShiftUiAction
}

