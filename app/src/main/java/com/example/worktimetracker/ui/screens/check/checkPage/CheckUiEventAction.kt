package com.example.worktimetracker.ui.screens.check.checkPage

import android.location.Location
import com.example.worktimetracker.data.remote.response.Shift

sealed interface CheckUiEvent {
    data object Success : CheckUiEvent
    data class Failure(val message: String) : CheckUiEvent
}

sealed interface CheckUiAction {
    data class Check(val checkType: Int) : CheckUiAction
    data class ChooseShift(var shift: Shift) : CheckUiAction
    data class UpdateCurrentLocation (var currentLocation: Location?) : CheckUiAction
}


