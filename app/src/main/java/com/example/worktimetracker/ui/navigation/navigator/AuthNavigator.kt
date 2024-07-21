package com.example.worktimetracker.ui.navigation.navigator

import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.worktimetracker.ui.screens.auth.register.RegisterScreen
import com.example.worktimetracker.ui.screens.auth.register.RegisterViewModel
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedViewModel

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
            LoginScreen(
                viewModel = loginViewModel,
                sharedViewModel = sharedViewModel,
                onLoginSuccess = {
                    navController.navigateAndClearStack(it.route)
                },
                onNavigateTo = {
                    navController.navigateSingleTopTo(it.route)
                }
            )
        }
        composable(
            route = Route.RegisterScreen.route
        ) {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(
                viewModel = registerViewModel,
                onRegisterSuccess = {
                    navController.navigateAndClearStack(it.route)
                },
                onNavigateTo = {
                    navController.navigateSingleTopTo(it.route)
                },
                onBack = {
                    navController.navigateSingleTopTo(Route.LoginScreen.route)
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