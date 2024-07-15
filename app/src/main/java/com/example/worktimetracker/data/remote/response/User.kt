package com.example.worktimetracker.data.remote.response

data class User(
    val address: String,
    val avatarURL: String,
    val company: Any,
    val companyId: Int,
    val companyTeam: Any,
    val createdAt: String,
    val department: String,
    val designation: String,
    val email: String,
    val employeeType: Int,
    val id: Int,
    val password: String,
    val phoneNumber: String,
    val role: Int,
    val teamId: Any,
    val userFullName: String,
    val userName: String
)