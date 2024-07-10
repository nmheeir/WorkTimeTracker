package com.example.worktimetracker.data.remote.response

data class User(
    val username: String,
    val job: String,
    val token: String? = null
)
