package com.example.worktimetracker.ui.screens.shift

import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.data.remote.response.Shift
import java.time.LocalDate

data class ShiftUiState(
    var isLoading : Boolean = true,
    var shiftList : List<Shift>? = emptyList(),
    val shiftMap: MutableMap<Int, MutableList<Shift>> = HashMap(),
    var datePicked : Int = LocalDate.now().dayOfMonth
)
