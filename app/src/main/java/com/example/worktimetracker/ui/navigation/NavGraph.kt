package com.example.worktimetracker.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.worktimetracker.ui.navigation.navigator.authNavigator
import com.example.worktimetracker.ui.screens.check.CheckScreen
import com.example.worktimetracker.ui.screens.home.HomeScreen
import com.example.worktimetracker.ui.screens.log.LogScreen
import com.example.worktimetracker.ui.screens.log.LogViewModel
import com.example.worktimetracker.ui.screens.onboarding.OnboardingScreen
import com.example.worktimetracker.ui.screens.onboarding.OnboardingViewModel
import com.example.worktimetracker.ui.screens.payroll.PayrollScreen
import com.example.worktimetracker.ui.screens.profile.ProfileScreen
import com.example.worktimetracker.ui.screens.profile.my_profile.MyProfileScreen
import com.example.worktimetracker.ui.screens.profile.setting.SettingScreen
import com.example.worktimetracker.ui.screens.profile.term_condition.PrivacyPolicyScreen
import com.example.worktimetracker.ui.screens.profile.term_condition.TermConditionScreen
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiEvent
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedViewModel

@Composable
fun NavGraph(
    sDestination: String,
    navController: NavHostController
) {
    //shared viewmodel
    val sharedViewModel: SharedViewModel = hiltViewModel()

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

        animatedComposable(
            route = Route.HomeScreen.route,
        ) {
            // TODO: cần tối ưu lại
            sharedViewModel.onEvent(SharedUiEvent.GetUserInfo)
            HomeScreen(
                state = sharedViewModel.state,
                onNavigateTo = {
                    navController.navigate(it.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        animatedComposable(route = Route.ProfileScreen.route) {
            ProfileScreen(
                onLogoutClick = {
                    navController.navigateAndClearStack(Route.AuthNavigator.route)
                },
                state = sharedViewModel.state,
                event = sharedViewModel::onEvent,
                onNavigateTo = {
                    navController.navigate(it.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        animatedComposable(route = Route.LogScreen.route) {
            val logViewModel: LogViewModel = hiltViewModel()
            LogScreen(
                state = logViewModel.state,
                onBack = {
                    navController.popBackStack()
                },
                event = logViewModel::onEvent
            )
        }

        composable(route = Route.CheckInScreen.route) {
            CheckScreen()
        }

        composable(route = Route.PayrollScreen.route) {
            PayrollScreen()
        }

        animatedComposable(route = Route.MyProfileScreen.route) {
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

