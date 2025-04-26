package com.example.worktimetracker.ui.navigation

sealed class Screens(
    val route: String,
) {
    //Onboarding Screen
    data object OnboardingScreen : Screens("Onboarding")

    //Auth Screen
    data object LoginScreen : Screens("Login")
    data object ForgotPasswordScreen : Screens("ForgotPassword")
    data object CreateNewPasswordScreen : Screens("Create New Password")


    //Home Screen
    data object HomeScreen : Screens("Home") {
        val screensWithArgs = "${Screens.HomeScreen.route}/{name}"
    }

    data object LeavesScreen : Screens("Leaves")
    data object ApplyLeaveScreen : Screens("ApplyLeave")
    data object AttendanceScreen : Screens("Attendance")
    data object ActivitySrceen : Screens("Activity")

    //Profile
    data object ProfileScreen : Screens("Profile")
    data object MyProfileScreen : Screens("MyProfile")
    data object SettingScreen : Screens("Setting")
    data object TermConditionScreen : Screens("TermCondition")
    data object PrivacyScreen : Screens("Privacy")
    //End Profile

    data object SalaryScreen : Screens("Salary")
    data object PayCheckDetail : Screens("PayCheckDetail")
    data object LogScreen : Screens("Log")
    data object WorkTimeScreen : Screens("WorkTime")
    data object ShiftScreen : Screens("Shift")

    // CheckScreen
    data object CheckScreen : Screens("Check")
    data object ShiftCheckScreen : Screens("ShiftCheck")

    //Notification
    data object Notification : Screens("Notification")

    //Task
    data object TaskScreen : Screens("Task")
    data object TaskDetailScreen : Screens("TaskDetail")
}