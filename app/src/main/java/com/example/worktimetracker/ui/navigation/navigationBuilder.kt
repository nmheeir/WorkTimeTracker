package com.example.worktimetracker.ui.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.worktimetracker.ui.screens.NotificationScreen
import com.example.worktimetracker.ui.screens.auth.LoginScreen
import com.example.worktimetracker.ui.screens.auth.forgotpw.ForgotPasswordScreen
import com.example.worktimetracker.ui.screens.auth.forgotpw.screen.CreateNewPasswordScreen
import com.example.worktimetracker.ui.screens.check.checkPage.CheckScreen
import com.example.worktimetracker.ui.screens.furlough.FurloughScreen
import com.example.worktimetracker.ui.screens.home.HomeScreen
import com.example.worktimetracker.ui.screens.log.LogScreen
import com.example.worktimetracker.ui.screens.onboarding.OnboardingScreen
import com.example.worktimetracker.ui.screens.profile.setting.SettingScreen
import com.example.worktimetracker.ui.screens.profile.term_condition.PrivacyPolicyScreen
import com.example.worktimetracker.ui.screens.profile.term_condition.TermConditionScreen
import com.example.worktimetracker.ui.screens.salary.SalaryScreen
import com.example.worktimetracker.ui.screens.shift.ShiftScreen
import com.example.worktimetracker.ui.screens.worktime.WorkTimeScreen

fun NavGraphBuilder.navigationBuilder(
    navController: NavHostController
) {
    composable(
        route = Screens.OnboardingScreen.route
    ) {
        OnboardingScreen(
            onNavigateTo = { navController.navigate(it.route) }
        )
    }

    composable(
        route = Screens.LoginScreen.route
    ) {
        LoginScreen(
            onNavigateTo = { navController.navigate(it.route) },
            onLoginSuccess = {
                navController.navigate(Screens.HomeScreen.route) {
                    popUpTo(Screens.LoginScreen.route) {
                        inclusive = true
                    }
                }
            }
        )
    }

    composable(
        route = Screens.ForgotPasswordScreen.route
    ) {
        ForgotPasswordScreen(
            onNavigateTo = { navController.navigate(it.route) },
            onBack = { navController.popBackStack() }
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
    ) {
        CreateNewPasswordScreen(
            onNavigateTo = { navController.navigate(it.route) },
            onBack = { navController.popBackStack() }
        )
    }

    composable(
        route = Screens.CheckScreen.route
    ) {
        CheckScreen(
            onCheckSuccess = { navController.navigate(it.route) },
            onNavigateTo = { navController.navigate(it.route) },
            onBack = { navController.popBackStack() }
        )
    }

    composable(
        route = Screens.HomeScreen.route
    ) {
        HomeScreen(navController = navController)
    }

    composable(
        route = Screens.ProfileScreen.route
    ) {
//        ProfileScreen() { }
    }

    composable(
        route = Screens.LogScreen.route
    ) {
        LogScreen(navController)
    }

    composable(
        route = Screens.WorkTimeScreen.route
    ) {
        WorkTimeScreen(navController)
    }

    composable(
        route = Screens.SalaryScreen.route
    ) {
        SalaryScreen(navController)
    }

    composable(
        route = Screens.PayCheckDetail.route
    ) {
        // TODO: Làm lại PayCheckDetailScreen
        Text(
            text = "PayCheck Detail Screen"
        )
    }

    composable(
        route = Screens.ShiftScreen.route
    ) {
        ShiftScreen(navController)
    }

    composable(
        route = Screens.MyProfileScreen.route
    ) {
        // TODO: Làm lại My Profile Screen
        Text(
            text = "My Profile Screen"
        )
    }

    composable(
        route = Screens.SettingScreen.route
    ) {
        SettingScreen(navController)
    }

    composable(route = Screens.TermConditionScreen.route) {
        TermConditionScreen(navController)
    }

    composable(route = Screens.PrivacyScreen.route) {
        PrivacyPolicyScreen(navController)
    }

    composable(route = Screens.Notification.route) {
        NotificationScreen(navController)
    }

    composable(route = Screens.ApplyLeaveScreen.route) {

    }
}