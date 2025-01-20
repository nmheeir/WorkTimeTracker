package com.example.worktimetracker.ui.screens.check.checkPage

import android.location.Location
import com.example.worktimetracker.data.remote.response.Shift

data class CheckUiState(
    var isShiftLoading: Boolean = true,
    var todayShifts: List<Shift> = emptyList(),

    var choosenShift: Shift? = null,

    var isChecking: Boolean = false,

    var currentLocation: Location? = null
)
