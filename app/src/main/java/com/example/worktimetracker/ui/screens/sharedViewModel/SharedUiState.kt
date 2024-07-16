package com.example.worktimetracker.ui.screens.sharedViewModel

import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.ui.util.exampleUser

data class SharedUiState(
    val user: User = exampleUser,
    val isLoading: Boolean = false
)
