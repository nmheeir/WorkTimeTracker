package com.example.worktimetracker.core.domain

data class PagingState(
    val pageNumber: Int = 1,
    val pageSize: Int = 10,
)