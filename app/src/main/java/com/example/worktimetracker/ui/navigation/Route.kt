package com.example.worktimetracker.ui.navigation

sealed class Route(
    val route : String,
) {
    //Onboarding Screen
    data object OnboardingScreen : Route("Onboarding")

    //Auth Screen
    data object LoginScreen : Route("Login")
    data object ForgotPasswordScreen : Route("ForgotPassword")

    //Home Screen
    data object HomeScreen : Route("Home") {
        val routeWithArgs = "${Route.HomeScreen.route}/{name}"
    }
    data object LeavesScreen : Route("Leaves")
     data object AttendanceScreen : Route("Attendance")
     data object ProfileScreen : Route("Profile")

    //Navigator
    data object AuthNavigator : Route("AuthNavigator")
    data object MainNavigator : Route("MainNavigator")

}