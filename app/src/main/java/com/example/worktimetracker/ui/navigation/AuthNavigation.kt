package com.example.worktimetracker.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.worktimetracker.ui.screens.auth.AuthScreen
import com.example.worktimetracker.ui.screens.auth.login.LoginScreen
import com.example.worktimetracker.ui.screens.auth.signup.SignUpScreen

fun NavGraphBuilder.authNavigation(navController: NavHostController) {
    navigation(
        startDestination = Route.AuthScreen.route,
        route = Route.AuthNavigation.route
    ) {
        composable(
            route = Route.AuthScreen.route
        ) {
            AuthScreen(navController = navController)
        }

        composable(
            route = Route.LoginScreen.route
        ) {
            LoginScreen(navController = navController)
        }

        composable(
            route = Route.SignUpScreen.route
        ) {
            SignUpScreen()
        }
    }
}