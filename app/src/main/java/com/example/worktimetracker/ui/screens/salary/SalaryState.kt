package com.example.worktimetracker.ui.screens.salary

import com.example.worktimetracker.data.remote.response.PayCheck

data class SalaryState(
    val isLoading: Boolean = false,
    val messageError: String? = null,
    val listPayCheck: List<PayCheck> = emptyList()
)
