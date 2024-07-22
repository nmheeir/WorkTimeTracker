package com.example.worktimetracker.data.remote.request

import com.example.worktimetracker.data.remote.response.LogType

data class CreateLogRequest(
    val userId : Int = 0,
    val time: String,
    val type: Int,
    val createAt: String
)
