package com.example.worktimetracker.data.remote.request

data class UserRegisterRequest(
    val username: String,
    val password: String,
    val email: String
)