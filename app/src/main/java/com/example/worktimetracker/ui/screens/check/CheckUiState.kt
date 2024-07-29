package com.example.worktimetracker.ui.screens.check

import com.example.worktimetracker.data.remote.response.Check

data class CheckUiState(
    var isCurrentStateLoaded : Boolean = false,
    var todayCheckList: List<Check> = emptyList()
)
