package com.example.worktimetracker.ui.util

import androidx.annotation.DrawableRes
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.enums.EmployeeType
import com.example.worktimetracker.data.remote.enums.Role
import com.example.worktimetracker.data.remote.request.UserUpdateRequest
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.ui.navigation.Screens

val exampleUser = User(
    address = "address",
    avatarUrl = "https://cdn.pixabay.com/photo/2016/09/07/10/37/kermit-1651325_1280.jpg",
    createdAt = "12-12-2024",
    department = "department",
    designation = "designation",
    email = "email",
    employeeType = EmployeeType.entries.random(),
    id = 0,
    password = "password",
    phoneNumber = "phoneNumber",
    role = Role.entries.random(),
    userFullName = "userFullName",
    userName = "userName"
)

val exampleUpdateUser = UserUpdateRequest(
    password = "password",
    address = "address",
    email = "email"
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
    @DrawableRes val icon: Int,
    val title: String,
    val screens: Screens? = null
)

val lOptionProfile = listOf(
    ProfileOption(
        icon = R.drawable.ic_user,
        title = "My Profile",
        screens = Screens.MyProfileScreen
    ),
    ProfileOption(
        icon = R.drawable.ic_setting,
        title = "Settings",
        screens = Screens.SettingScreen
    ),
    ProfileOption(
        icon = R.drawable.ic_file,
        title = "Terms & Conditions",
        screens = Screens.TermConditionScreen
    ),
    ProfileOption(
        icon = R.drawable.ic_privacy_policy,
        title = "Privacy Policy",
        screens = Screens.PrivacyScreen
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

data class WorkTimeData(
    val date: String,
    val workTime: List<Int>
)

val exampleWorkTime = listOf(
    WorkTimeData(
        date = "Apr 10, 2023",
        workTime = listOf(4, 4)
    ),
    WorkTimeData(
        date = "Apr 11, 2023",
        workTime = listOf(4, 8)
    ),
    WorkTimeData(
        date = "Apr 12, 2023",
        workTime = listOf(4)
    ),
    WorkTimeData(
        date = "Apr 13, 2023",
        workTime = listOf(4, 6)
    ),
    WorkTimeData(
        date = "Apr 14, 2023",
        workTime = listOf(4, 12)
    ),
)
