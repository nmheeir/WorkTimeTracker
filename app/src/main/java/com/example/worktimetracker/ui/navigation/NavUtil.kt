package com.example.worktimetracker.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
    popUpTo(
        this@navigateSingleTopTo.graph.findStartDestination().id
    ) {
        saveState = true
    }
}

fun NavHostController.navigateAndClearStack(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
    popUpTo(0) {
        inclusive = true
    }
}