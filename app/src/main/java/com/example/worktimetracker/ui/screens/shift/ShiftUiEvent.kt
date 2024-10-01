package com.example.worktimetracker.ui.screens.shift

sealed class ShiftUiEvent (){
    data class GetMyShiftsInMonth(val month : Int,val year : Int) : ShiftUiEvent()
    data class GetMyChecksInMonth(val month : Int,val year : Int) : ShiftUiEvent()
    data class DialogToggle(val date : Int) : ShiftUiEvent()
}

