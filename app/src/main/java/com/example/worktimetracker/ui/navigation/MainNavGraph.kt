package com.example.worktimetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.worktimetracker.ui.screens.attendance.AttendanceScreen
import com.example.worktimetracker.ui.screens.home.HomeContent
import com.example.worktimetracker.ui.screens.leaves.LeavesScreen
import com.example.worktimetracker.ui.screens.profile.ProfileScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        route = Route.MainNavigation.route,
        navController = navController,
        startDestination = Route.HomeScreen.route
    ) {
        composable(Route.HomeScreen.route) {
            HomeContent()
        }
        composable(Route.ProfileScreen.route) {
            ProfileScreen()
        }
        composable(Route.AttendanceScreen.route) {
            AttendanceScreen()
        }
        composable(Route.LeavesScreen.route) {
            LeavesScreen()
        }
    }
}