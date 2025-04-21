package com.example.worktimetracker.data.remote.response

data class Team(
    val id: Int,
    val name: String,
    val companyId: Int,
    val company: Company,
    val users: List<User>
)
