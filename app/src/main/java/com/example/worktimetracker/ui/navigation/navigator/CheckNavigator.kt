package com.example.worktimetracker.ui.navigation.navigator

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.worktimetracker.ui.navigation.Screens
import com.example.worktimetracker.ui.navigation.navigateSingleTopTo
import com.example.worktimetracker.ui.screens.check.checkPage.CheckScreen
import com.example.worktimetracker.ui.viewmodels.CheckViewModel
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedViewModel

fun NavGraphBuilder.checkNavigator(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
) {
    navigation(
        startDestination = Screens.CheckScreen.route,
        route = Screens.CheckNavigator.route
    ) {
        composable(route = Screens.CheckScreen.route) {
            val viewModel: CheckViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            CheckScreen(
                state = state,
                channel = viewModel.channel,
                action = viewModel::onAction,
                onCheckSuccess = {
                    navController.popBackStack(it.route, false)
                },
                onNavigateTo = {
                    navController.navigateSingleTopTo(it.route)
                },
                onBack = {
                    navController.popBackStack()
                },
            )
        }
    }

}