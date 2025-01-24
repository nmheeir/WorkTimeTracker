package com.example.worktimetracker.ui.screens.sharedViewModel

import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.ui.util.exampleUser

data class SharedUiState(
    val user: User = exampleUser,
    val updateAddress: String = "",
    val updateEmail: String = "",
    val updatePassword: String = "",
    val isUpdateEmailValid: Boolean = true,
    val isUpdatePasswordValid: Boolean = false,
    val isLoading: Boolean = false
)
