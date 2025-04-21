package com.example.worktimetracker.ui.navigation.navigator

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.example.worktimetracker.ui.navigation.Screens
import com.example.worktimetracker.ui.navigation.navigateAndClearStack
import com.example.worktimetracker.ui.navigation.navigateSingleTopTo
import com.example.worktimetracker.ui.screens.auth.forgotpw.ForgotPasswordScreen
import com.example.worktimetracker.ui.screens.auth.forgotpw.ForgotPasswordUiAction
import com.example.worktimetracker.ui.viewmodels.ForgotPasswordViewModel
import com.example.worktimetracker.ui.screens.auth.forgotpw.screen.CreateNewPasswordScreen
import com.example.worktimetracker.ui.screens.auth.forgotpw.screen.ErrorScreen
import com.example.worktimetracker.ui.screens.auth.login.LoginScreen
import com.example.worktimetracker.ui.viewmodels.LoginViewModel
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedViewModel
import com.example.worktimetracker.ui.util.JwtUtils

fun NavGraphBuilder.authNavigator(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    navigation(
        startDestination = Screens.LoginScreen.route,
        route = Screens.AuthNavigator.route
    ) {
        composable(
            route = Screens.LoginScreen.route
        ) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            val state by loginViewModel.state.collectAsStateWithLifecycle()
            LoginScreen(
                state = state,
                channel = loginViewModel.channel,
                action = loginViewModel::onAction,
                onLoginSuccess = {
                    navController.navigateAndClearStack(Screens.HomeScreen.route)
                },
                onNavigateTo = {
                    navController.navigateSingleTopTo(it.route)
                }
            )
        }


        composable(
            route = Screens.ForgotPasswordScreen.route
        ) {
            val viewModel: ForgotPasswordViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            ForgotPasswordScreen(
                channel = viewModel.channel,
                action = viewModel::onAction,
                state = state,
                onNavigateTo = {
                    navController.navigate(it.route) {
                        popUpTo(navController.currentDestination?.route!!) {
                            inclusive = true
                        }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screens.CreateNewPasswordScreen.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern =
                        "http://localhost:5260/api/Auth/resetPassword?token={token}&token2={token2}"
                }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token")
            val token2 = backStackEntry.arguments?.getString("token2")

            Log.d(TAG, "token : $token \n token2 :$token2")

            if (token != null && token2 != null &&
                !JwtUtils.isTokenExpired(token) &&
                !JwtUtils.isTokenExpired(token2)
            ) {


                val viewModel: ForgotPasswordViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()

                // Cập nhật token2 vào ViewModel
                viewModel.onAction(ForgotPasswordUiAction.UpdateToken(token2))

                // Mở màn hình CreateNewPasswordScreen nếu token hợp lệ
                CreateNewPasswordScreen(
                    channel = viewModel.channel2,
                    onBack = {
                        navController.navigateAndClearStack(Screens.LoginScreen.route)
                    },
                    action = viewModel::onAction,
                    onNavigateTo = {
                        navController.navigateAndClearStack(it.route)
                    },
                    state = state
                )
//                ErrorScreen()
            } else {
                Log.d(TAG, "token: " + JwtUtils.isTokenExpired(token!!).toString())
                Log.d(TAG, "token2: " + JwtUtils.isTokenExpired(token2!!).toString())
                ErrorScreen()
            }
        }
    }
}