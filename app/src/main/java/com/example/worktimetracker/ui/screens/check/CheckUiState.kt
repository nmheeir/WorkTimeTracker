package com.example.worktimetracker.ui.screens.check

import com.example.worktimetracker.data.remote.response.Check

data class CheckUiState(
    var isCurrentStateLoaded : Boolean = false,
    var day : Int? = null,
    var month : Int? = null,
    var year : Int? = null,
    var todayCheckList: List<Check> = emptyList()
)
