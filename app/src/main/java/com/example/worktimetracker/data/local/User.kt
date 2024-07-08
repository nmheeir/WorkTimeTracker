package com.example.worktimetracker.data.local

import androidx.annotation.DrawableRes

data class User(
    val username: String,
    @DrawableRes val avatar: Int,
    val job: String
)
