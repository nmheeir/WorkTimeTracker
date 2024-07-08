package com.example.worktimetracker.ui.util

import com.example.worktimetracker.R
import com.example.worktimetracker.data.local.User

val exampleUser = User(
    username = "John Doe",
    avatar = R.drawable.avatar,
    job = "Developer"
)

data class Leaves(
    val title: String,
    val count: Int
)

val listLeaves = listOf(
    Leaves("Leave Balance", 20),
    Leaves("Leave Approved", 10),
    Leaves("Leave Pending", 5),
    Leaves("Leave Cancelled", 3),
)

data class LeavesDetail(
    val date: String,
    val isApproved: Boolean,
    val applyDays: Int,
    val leaveBalance: Int,
    val approvedBy: User
)

val lLeavesDetail = listOf(
    LeavesDetail(
        date = "Apr 10, 2023 - Apr 15, 2023",
        isApproved = true,
        applyDays = 5,
        leaveBalance = 10,
        approvedBy = exampleUser
    ),
    LeavesDetail(
        date = "Apr 10, 2023 - Apr 15, 2023",
        isApproved = true,
        applyDays = 5,
        leaveBalance = 10,
        approvedBy = exampleUser
    ),
    LeavesDetail(
        date = "Apr 10, 2023 - Apr 15, 2023",
        isApproved = true,
        applyDays = 5,
        leaveBalance = 10,
        approvedBy = exampleUser
    ),
    LeavesDetail(
        date = "Mar 20, 2023 - Apr 1, 2023",
        isApproved = false,
        applyDays = 5,
        leaveBalance = 10,
        approvedBy = exampleUser
    ),
    LeavesDetail(
        date = "Mar 20, 2023 - Apr 1, 2023",
        isApproved = false,
        applyDays = 5,
        leaveBalance = 10,
        approvedBy = exampleUser
    ),
    LeavesDetail(
        date = "Mar 20, 2023 - Apr 1, 2023",
        isApproved = false,
        applyDays = 5,
        leaveBalance = 10,
        approvedBy = exampleUser
    )
)