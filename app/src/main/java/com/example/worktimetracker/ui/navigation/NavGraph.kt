package com.example.worktimetracker.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.worktimetracker.ui.navigation.navigator.authNavigator
import com.example.worktimetracker.ui.screens.attendance.AttendanceScreen
import com.example.worktimetracker.ui.screens.home.AppScaffold
import com.example.worktimetracker.ui.screens.home.HomeContent
import com.example.worktimetracker.ui.screens.leaves.LeavesScreen
import com.example.worktimetracker.ui.screens.onboarding.OnboardingScreen
import com.example.worktimetracker.ui.screens.onboarding.OnboardingViewModel
import com.example.worktimetracker.ui.screens.profile.ProfileScreen
import com.example.worktimetracker.ui.screens.profile.my_profile.MyProfileScreen
import com.example.worktimetracker.ui.screens.profile.setting.SettingScreen
import com.example.worktimetracker.ui.screens.profile.term_condition.PrivacyPolicyScreen
import com.example.worktimetracker.ui.screens.profile.term_condition.TermConditionScreen
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiEvent
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedViewModel

@Composable
fun NavGraph(
    sDestination: String
) {
    val navController = rememberNavController()

    //shared viewmodel
    val sharedViewModel : SharedViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = sDestination) {
        composable(route = Route.OnboardingScreen.route) {
            val onboardingViewModel: OnboardingViewModel = hiltViewModel()
            OnboardingScreen(
                onNavigateTo = {
                    navController.navigateAndClearStack(it.route)
                },
                event = onboardingViewModel::onEvent
            )
        }

        authNavigator(navController, sharedViewModel)

        composable(route = Route.MainNavigator.route) {
            // TODO: cần tối ưu lại
            sharedViewModel.onEvent(SharedUiEvent.GetUserInfo)
            Log.d("viewmodel_home", "composable app scaffold")
            AppScaffold(
                logout = {
                    Log.d("viewModel_home_state", sharedViewModel.state.toString())
                    sharedViewModel.onEvent(SharedUiEvent.Logout)
                    navController.navigate(Route.AuthNavigator.route) {
                        popUpTo(Route.MainNavigator.route) {
                            inclusive = true
                        }
                    }
                },
                viewModel = sharedViewModel,
                onNavigateTo = {
                    navController.navigateSingleTopTo(it.route)
                }
            )
        }

        composable(route = Route.MyProfileScreen.route) {
            Log.d("viewmodel_home", "composable my profile")
            MyProfileScreen(
                state = sharedViewModel.state,
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
    sharedViewModel: SharedViewModel,
    onNavigateTo: (Route) -> Unit
) {

    NavHost(navController = navController, startDestination = Route.HomeScreen.route) {
        composable(route = Route.HomeScreen.route) {
            Log.d("viewmodel_home", "composable home screen")
            HomeContent(
                state = sharedViewModel.state
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
                state = sharedViewModel.state,
                event = sharedViewModel::onEvent,
                onNavigateTo = onNavigateTo
            )
        }
        authNavigator(navController, sharedViewModel)
    }
}

