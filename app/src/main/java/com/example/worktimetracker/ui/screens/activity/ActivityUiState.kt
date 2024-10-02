package com.example.worktimetracker.ui.screens.activity

import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.data.remote.response.CheckType
import java.time.LocalDate

data class ActivityUiState(
    var isLoaded: Boolean = false,
    var checkList: List<Check> = emptyList(),
    var fromDate: java.time.LocalDate? = LocalDate.now().withDayOfMonth(1),
    var toDate: java.time.LocalDate? = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()),
    var type: CheckType? = null,
)