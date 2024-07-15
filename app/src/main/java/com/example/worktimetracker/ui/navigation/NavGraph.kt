package com.example.worktimetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.worktimetracker.ui.screens.attendance.AttendanceScreen
import com.example.worktimetracker.ui.screens.home.AppScaffold
import com.example.worktimetracker.ui.screens.home.HomeContent
import com.example.worktimetracker.ui.screens.home.HomeViewModel
import com.example.worktimetracker.ui.screens.leaves.LeavesScreen
import com.example.worktimetracker.ui.screens.onboarding.OnboardingScreen
import com.example.worktimetracker.ui.screens.onboarding.OnboardingViewModel
import com.example.worktimetracker.ui.screens.profile.ProfileScreen

@Composable
fun NavGraph(
    sDestination: String,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = sDestination) {
        composable(route = Route.OnboardingScreen.route) {
            val viewModel: OnboardingViewModel = hiltViewModel()
            OnboardingScreen(
                onNavigateTo = {
                    navController.navigateAndClearStack(it.route)
                },
                event = viewModel::onEvent
            )
        }

        authNavigator(navController)
        composable(route = Route.MainNavigator.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            AppScaffold(
                logout = {
                    navController.navigate(Route.AuthNavigator.route) {
                        popUpTo(Route.MainNavigator.route) {
                            inclusive = true
                        }
                    }
                },
                viewModel = homeViewModel
            )
        }
    }
}

@Composable
fun HomeNavGraph(
    navController: NavHostController = rememberNavController(),
    logout: () -> Unit,
    homeViewModel: HomeViewModel
) {
    NavHost(navController = navController, startDestination = Route.HomeScreen.route) {
        composable(route = Route.HomeScreen.route) {
            HomeContent(
                state = homeViewModel.state
            )
        }
        composable(route = Route.LeavesScreen.route) {
            LeavesScreen()
        }
        composable(route = Route.AttendanceScreen.route) {
            AttendanceScreen()
        }
        composable(route = Route.ProfileScreen.route) {
            ProfileScreen(
                onLogoutClick = {
                    logout()
                },
                state = homeViewModel.state,
                event = homeViewModel::onEvent
            )
        }
        authNavigator(navController)
    }
}

