package com.example.worktimetracker.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.worktimetracker.ui.screens.auth.forgotpw.ForgotPasswordScreen
import com.example.worktimetracker.ui.screens.auth.login.LoginScreen
import com.example.worktimetracker.ui.screens.auth.login.LoginViewModel

fun NavGraphBuilder.authNavigator(navController: NavHostController) {
    navigation(
        startDestination = Route.LoginScreen.route,
        route = Route.AuthNavigator.route
    ) {
        composable(
            route = Route.LoginScreen.route
        ) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigateAndClearStack(it.route)
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