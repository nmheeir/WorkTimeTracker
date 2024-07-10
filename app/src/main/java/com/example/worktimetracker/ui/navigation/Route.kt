package com.example.worktimetracker.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.worktimetracker.R

sealed class Route(
    val route : String,
) {
    //Auth Screen
    data object AuthScreen : Route("Auth")
    data object LoginScreen : Route("Login")
    data object SignUpScreen : Route("SignUp")

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