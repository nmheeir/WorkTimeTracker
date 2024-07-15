package com.example.worktimetracker.ui.navigation.navigator

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.worktimetracker.ui.navigation.Route

fun NavGraphBuilder.mainNavigator(
    navController: NavHostController
) {
    navigation(
        route = Route.MainNavigator.route,
        startDestination = Route.HomeScreen.route
    ) {

    }
}