package com.example.worktimetracker.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.worktimetracker.ui.screens.auth.AuthScreen
import com.example.worktimetracker.ui.screens.auth.login.LoginScreen
import com.example.worktimetracker.ui.screens.auth.login.LoginViewModel
import com.example.worktimetracker.ui.screens.auth.signup.SignUpScreen

fun NavGraphBuilder.authNavigator(navController: NavHostController) {
    navigation(
        startDestination = Route.AuthScreen.route,
        route = Route.AuthNavigator.route
    ) {
        composable(
            route = Route.AuthScreen.route
        ) {
            AuthScreen(
                onNavigateTo = {
                    navController.navigateSingleTopTo(it.route)
                }
            )
        }
        composable(
            route = Route.LoginScreen.route
        ) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigateAndClearStack(it.route)
                }
            )
        }
        composable(
            route = Route.SignUpScreen.route
        ) {
            SignUpScreen()
        }
    }
}