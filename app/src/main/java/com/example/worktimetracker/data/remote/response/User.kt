package com.example.worktimetracker.data.remote.response

import com.example.worktimetracker.data.remote.enums.EmployeeType
import com.example.worktimetracker.data.remote.enums.Role

data class User(
    val address: String,
    val avatarUrl: String? = null,
    val company: Company? = null,
    val companyId: Int? = null,
    val companyTeam: Team? = null,
    val createdAt: String,
    val department: String,
    val designation: String,
    val email: String,
    val employeeType: EmployeeType,
    val id: Int,
    val password: String,
    val phoneNumber: String,
    val role: Role,
    val teamId: Int? = null,
    val userFullName: String,
    val userName: String
)