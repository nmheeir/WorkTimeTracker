package com.example.worktimetracker.ui.screens.check

import com.example.worktimetracker.data.remote.response.Check

data class CheckUiState(
    var day : Int? = null,
    var month : Int? = null,
    var year : Int? = null,
    var todayCheckList: List<Check> = emptyList(),

    var isTodayCheckListLoading: Boolean = false,
    var isChecking: Boolean = false,

    var isMapLoading: Boolean = false

)
