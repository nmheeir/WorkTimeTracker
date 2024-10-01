package com.example.worktimetracker.ui.screens.shift

import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.data.remote.response.Shift

data class ShiftState(

    var isLoading : Boolean = true,
    var shiftList : List<Shift>? = emptyList(),
    val shiftMap: Map<Int, List<Shift>?>? = shiftList?.groupBy { it.day },
    var checkList : List<Check>? = emptyList(),
    val checkMap: Map<Int, List<Check>?>? = checkList?.groupBy { it.getDate() },
    var isDialogShow : Boolean = false,
    var dialogDate : Int = 0
)
