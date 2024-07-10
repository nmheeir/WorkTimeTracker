package com.example.worktimetracker.ui.util

import androidx.annotation.DrawableRes
import com.example.worktimetracker.R

data class User(
    val username: String,
    @DrawableRes val avatar: Int,
    val job: String
)


val exampleUser = User(
    username = "John Doe", avatar = R.drawable.avatar, job = "Developer"
)

data class Leaves(
    val title: String, val count: Int
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
    ), LeavesDetail(
        date = "Apr 10, 2023 - Apr 15, 2023",
        isApproved = true,
        applyDays = 5,
        leaveBalance = 10,
        approvedBy = exampleUser
    ), LeavesDetail(
        date = "Apr 10, 2023 - Apr 15, 2023",
        isApproved = true,
        applyDays = 5,
        leaveBalance = 10,
        approvedBy = exampleUser
    ), LeavesDetail(
        date = "Mar 20, 2023 - Apr 1, 2023",
        isApproved = false,
        applyDays = 5,
        leaveBalance = 10,
        approvedBy = exampleUser
    ), LeavesDetail(
        date = "Mar 20, 2023 - Apr 1, 2023",
        isApproved = false,
        applyDays = 5,
        leaveBalance = 10,
        approvedBy = exampleUser
    ), LeavesDetail(
        date = "Mar 20, 2023 - Apr 1, 2023",
        isApproved = false,
        applyDays = 5,
        leaveBalance = 10,
        approvedBy = exampleUser
    )
)

data class ProfileOption(
    @DrawableRes val icon: Int, val title: String
)

val lOptionProfile = listOf(
    ProfileOption(
        icon = R.drawable.ic_user, title = "My Profile"
    ),
    ProfileOption(icon = R.drawable.ic_setting, title = "Settings"),
    ProfileOption(
        icon = R.drawable.ic_file,
        title = "Terms & Conditions"
    ),
    ProfileOption(
        icon = R.drawable.ic_privacy_policy,
        title = "Privacy Policy"
    ),
    ProfileOption(
        icon = R.drawable.ic_logout,
        title = "Log out"
    )
)

data class Attendance(
    val title: String,
    @DrawableRes val icon: Int,
    val time: String,
    val desc: String
)

val listAttendance = listOf(
    Attendance(
        title = "Check In",
        icon = R.drawable.ic_google,
        time = "08:00",
        desc = "Check In"
    ),
    Attendance(
        title = "Check Out",
        icon = R.drawable.ic_google,
        time = "17:00",
        desc = "Check Out"
    ),
    Attendance(
        title = "Break Time",
        icon = R.drawable.ic_google,
        time = "12:30",
        desc = "Avg 30 minutes"
    ),
    Attendance(
        title = "Total Days",
        icon = R.drawable.ic_google,
        time = "28",
        desc = "Working day"
    )
)
