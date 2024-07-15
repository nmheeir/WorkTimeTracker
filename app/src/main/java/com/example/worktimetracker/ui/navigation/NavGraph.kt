package com.example.worktimetracker.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.worktimetracker.ui.navigation.navigator.authNavigator
import com.example.worktimetracker.ui.screens.attendance.AttendanceScreen
import com.example.worktimetracker.ui.screens.home.AppScaffold
import com.example.worktimetracker.ui.screens.home.HomeContent
import com.example.worktimetracker.ui.screens.home.HomeUiEvent
import com.example.worktimetracker.ui.screens.home.HomeViewModel
import com.example.worktimetracker.ui.screens.leaves.LeavesScreen
import com.example.worktimetracker.ui.screens.onboarding.OnboardingScreen
import com.example.worktimetracker.ui.screens.onboarding.OnboardingViewModel
import com.example.worktimetracker.ui.screens.profile.ProfileScreen
import com.example.worktimetracker.ui.screens.profile.my_profile.MyProfileScreen
import com.example.worktimetracker.ui.screens.profile.setting.SettingScreen
import com.example.worktimetracker.ui.screens.profile.term_condition.PrivacyPolicyScreen
import com.example.worktimetracker.ui.screens.profile.term_condition.TermConditionScreen

@Composable
fun NavGraph(
    sDestination: String,
) {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()

    LaunchedEffect(homeViewModel, homeViewModel.state) {
        homeViewModel.onEvent(HomeUiEvent.GetUserInfo)
    }

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
            Log.d("viewmodel_home", "composable app scaffold")
            AppScaffold(
                logout = {
                    navController.navigate(Route.AuthNavigator.route) {
                        popUpTo(Route.MainNavigator.route) {
                            inclusive = true
                        }
                    }
                },
                viewModel = homeViewModel,
                onNavigateTo = {
                    navController.navigateSingleTopTo(it.route)
                }
            )
        }

        composable(route = Route.MyProfileScreen.route) {
            Log.d("viewmodel_home", "composable my profile")
            MyProfileScreen(
                state = homeViewModel.state,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Route.SettingScreen.route) {
            SettingScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Route.TermConditionScreen.route) {
            TermConditionScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Route.PrivacyScreen.route) {
            PrivacyPolicyScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun HomeNavGraph(
    navController: NavHostController = rememberNavController(),
    logout: () -> Unit,
    homeViewModel: HomeViewModel,
    onNavigateTo: (Route) -> Unit
) {

    NavHost(navController = navController, startDestination = Route.HomeScreen.route) {
        composable(route = Route.HomeScreen.route) {
            Log.d("viewmodel_home", "composable home screen")
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
            Log.d("viewmodel_home", "composable profile screen")
            ProfileScreen(
                onLogoutClick = {
                    logout()
                },
                state = homeViewModel.state,
                event = homeViewModel::onEvent,
                onNavigateTo = onNavigateTo
            )
        }
        authNavigator(navController)
    }
}

