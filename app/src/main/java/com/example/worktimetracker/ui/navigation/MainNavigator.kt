package com.example.worktimetracker.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.worktimetracker.ui.screens.attendance.AttendanceScreen
import com.example.worktimetracker.ui.screens.home.HomeContent
import com.example.worktimetracker.ui.screens.leaves.LeavesScreen
import com.example.worktimetracker.ui.screens.profile.ProfileScreen

fun NavGraphBuilder.mainNavigator(
    navController: NavHostController
) {
    navigation(
        route = Route.MainNavigator.route,
        startDestination = Route.HomeScreen.route
    ) {

    }
}