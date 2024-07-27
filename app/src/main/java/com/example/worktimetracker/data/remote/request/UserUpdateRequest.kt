package com.example.worktimetracker.data.remote.request

data class UserUpdateRequest (
    val password: String,
    val address: String,
    val email: String
)