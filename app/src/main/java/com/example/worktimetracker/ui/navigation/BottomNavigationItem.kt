package com.example.worktimetracker.ui.navigation

import androidx.annotation.DrawableRes
import com.example.worktimetracker.R

data class BottomNavigationItem (
    val route: String,
    @DrawableRes val icon : Int
)

val lNavItem = listOf(
    BottomNavigationItem(Route.HomeScreen.route, R.drawable.ic_home),
    BottomNavigationItem(Route.LeavesScreen.route, R.drawable.ic_calendar),
    BottomNavigationItem(Route.AttendanceScreen.route, R.drawable.ic_search),
    BottomNavigationItem(Route.ProfileScreen.route, R.drawable.ic_user)

)