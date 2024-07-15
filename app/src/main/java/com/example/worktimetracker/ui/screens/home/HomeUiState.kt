package com.example.worktimetracker.ui.screens.home

import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.ui.util.exampleUser

data class HomeUiState(
    val user: User = exampleUser,
    val isLoading: Boolean = false
)
