package com.example.worktimetracker.ui.navigation

sealed class Route(
    val route : String,
) {
    //Onboarding Screen
    data object OnboardingScreen : Route("Onboarding")

    //Auth Screen
    data object LoginScreen : Route("Login")
    data object ForgotPasswordScreen : Route("ForgotPassword")
    data object RegisterScreen : Route("Register")

    //Home Screen
    data object HomeScreen : Route("Home") {
        val routeWithArgs = "${Route.HomeScreen.route}/{name}"
    }
    data object LeavesScreen : Route("Leaves")
     data object AttendanceScreen : Route("Attendance")

    //Profile
    data object UpdateProfileScreen: Route("UpdateProfile")
     data object ProfileScreen : Route("Profile")
    data object MyProfileScreen : Route("MyProfile")
    data object SettingScreen : Route("Setting")
    data object TermConditionScreen : Route("TermCondition")
    data object PrivacyScreen : Route("Privacy")
    //End Profile

    data object SalaryScreen : Route("Salary")
    data object CheckInScreen : Route("CheckIn")
    data object LogScreen : Route("Log")
    data object WorkTimeScreen : Route("WorkTime")
    data object ShiftScreen : Route("Shift")


    //Navigator
    data object AuthNavigator : Route("AuthNavigator")

}