package com.example.worktimetracker.ui.navigation.navigator

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.navigation.navigateAndClearStack
import com.example.worktimetracker.ui.navigation.navigateSingleTopTo
import com.example.worktimetracker.ui.screens.auth.forgotpw.ForgotPasswordScreen
import com.example.worktimetracker.ui.screens.auth.login.LoginScreen
import com.example.worktimetracker.ui.screens.auth.login.LoginViewModel
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedViewModel
import kotlin.math.log

fun NavGraphBuilder.authNavigator(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    navigation(
        startDestination = Route.LoginScreen.route,
        route = Route.AuthNavigator.route
    ) {
        composable(
            route = Route.LoginScreen.route
        ) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            val state by loginViewModel.state.collectAsStateWithLifecycle()
            LoginScreen(
                state = state,
                channel = loginViewModel.channel,
                action = loginViewModel::onAction,
                onLoginSuccess = {
                    navController.navigateAndClearStack(Route.HomeScreen.route)
                },
                onNavigateTo = {
                    navController.navigateSingleTopTo(it.route)
                }
            )
        }


        composable(
            route = Route.ForgotPasswordScreen.route
        ) {
            ForgotPasswordScreen(
                onBack = {
                    navController.navigateSingleTopTo(Route.LoginScreen.route)
                }
            )
        }
    }
}