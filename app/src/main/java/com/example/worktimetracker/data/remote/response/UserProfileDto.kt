package com.example.worktimetracker.data.remote.response

data class UserProfileDto(
    val id: Int,
    val userName: String,
    val userFullName: String,
    val avatarUrl: String,
)