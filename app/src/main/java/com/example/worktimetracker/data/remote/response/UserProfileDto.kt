package com.example.worktimetracker.data.remote.response

data class UserProfileDto(
    val id: Int,
    val userName: String,
    val userFullName: String,
    val avatarUrl: String,
)

val fakeUserProfileDto = UserProfileDto(
    id = 1,
    userName = "Alice",
    userFullName = "Alice Johnson",
    avatarUrl = "https://picsum.photos/200"
)