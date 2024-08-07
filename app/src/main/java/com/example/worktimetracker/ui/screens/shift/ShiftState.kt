package com.example.worktimetracker.ui.screens.shift

import com.example.worktimetracker.data.remote.response.Shift

data class ShiftState(
    var isLoading : Boolean = true,
    var shiftList : List<Shift>? = emptyList(),
    val shiftMap: Map<Int, List<Shift>?>? = shiftList?.groupBy { it.day }
)
