package com.example.worktimetracker.ui.screens.home.components.ActivitySection

import com.example.worktimetracker.data.remote.response.Check


data class ActivitySectionUiState(
    var isLoaded : Boolean = false,
    var activityItems : List<Check> = emptyList()
)
