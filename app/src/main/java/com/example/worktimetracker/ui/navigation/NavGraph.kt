package com.example.worktimetracker.ui.navigation

import OnboardingScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.ui.component.background.LinearBackground
import com.example.worktimetracker.ui.navigation.navigator.authNavigator
import com.example.worktimetracker.ui.navigation.navigator.checkNavigator
import com.example.worktimetracker.ui.screens.home.HomeScreen
import com.example.worktimetracker.ui.screens.log.LogScreen
import com.example.worktimetracker.ui.screens.log.LogViewModel
import com.example.worktimetracker.ui.screens.onboarding.OnboardingViewModel
import com.example.worktimetracker.ui.screens.profile.ProfileScreen
import com.example.worktimetracker.ui.screens.profile.my_profile.MyProfileScreen
import com.example.worktimetracker.ui.screens.profile.setting.SettingScreen
import com.example.worktimetracker.ui.screens.profile.term_condition.PrivacyPolicyScreen
import com.example.worktimetracker.ui.screens.profile.term_condition.TermConditionScreen
import com.example.worktimetracker.ui.screens.salary.SalaryScreen
import com.example.worktimetracker.ui.screens.salary.SalaryViewModel
import com.example.worktimetracker.ui.screens.salary.component.PayCheckDetail
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiEvent
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedViewModel
import com.example.worktimetracker.ui.screens.shift.ShiftScreen
import com.example.worktimetracker.ui.screens.shift.ShiftViewModel
import com.example.worktimetracker.ui.screens.worktime.WorkTimeScreen
import com.example.worktimetracker.ui.screens.worktime.WorkTimeViewModel

@Composable
fun NavGraph(
    sDestination: String, navController: NavHostController
) {
    //shared viewmodel
    val sharedViewModel: SharedViewModel = hiltViewModel()
    LinearBackground {
        NavHost(navController = navController, startDestination = sDestination) {
            composable(route = Route.OnboardingScreen.route) {
                val onboardingViewModel: OnboardingViewModel = hiltViewModel()
                OnboardingScreen(
                    onNavigateTo = {
                        navController.navigateAndClearStack(it.route)
                    }, event = onboardingViewModel::onEvent
                )
            }

            authNavigator(navController, sharedViewModel)
            checkNavigator(navController, sharedViewModel)

            animatedComposable(
                route = Route.HomeScreen.route,
            ) {
                // TODO: cần tối ưu lại
                sharedViewModel.onEvent(SharedUiEvent.GetUserInfo)
                HomeScreen(state = sharedViewModel.state, onNavigateTo = {
                    navController.navigate(it.route) {
                        launchSingleTop = true
                    }
                })
            }

            animatedComposable(route = Route.ProfileScreen.route) {
                ProfileScreen(onLogoutClick = {
                    sharedViewModel.onEvent(SharedUiEvent.Logout)
                    navController.navigateAndClearStack(Route.AuthNavigator.route)
                }, state = sharedViewModel.state, event = sharedViewModel::onEvent, onNavigateTo = {
                    navController.navigate(it.route) {
                        launchSingleTop = true
                    }
                })
            }

            animatedComposable(route = Route.LogScreen.route) {
                val logViewModel: LogViewModel = hiltViewModel()
                val logState by logViewModel.state.collectAsStateWithLifecycle()
                LogScreen(
                    state = logState,
                    onBack = {
                        navController.popBackStack()
                    },
                    action = logViewModel::onAction,
                    channel = logViewModel.channel
                )
            }

            animatedComposable(route = Route.WorkTimeScreen.route) {
                val workTimeViewModel: WorkTimeViewModel = hiltViewModel()
                val workTimeState by workTimeViewModel.state.collectAsStateWithLifecycle()
                WorkTimeScreen(state = workTimeState, onBack = {
                    navController.popBackStack()
                }, channel = workTimeViewModel.channel, action = workTimeViewModel::onAction)
            }



            animatedComposable(route = Route.SalaryScreen.route) {
                val salaryViewModel: SalaryViewModel = hiltViewModel()
                val salaryState by salaryViewModel.state.collectAsStateWithLifecycle()
                SalaryScreen(
                    state = salaryState,
                    onBack = {
                        navController.popBackStack()
                    },
                    onShowPayCheckDetail = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "paycheck", value = it
                        )
                        navController.navigate(Route.PayCheckDetail.route)
                    }
                )
            }

            animatedComposable(route = Route.PayCheckDetail.route) {
                val paycheck =
                    navController.previousBackStackEntry?.savedStateHandle?.get<PayCheck>("paycheck")
                PayCheckDetail(
                    paycheck = paycheck!!,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            animatedComposable(route = Route.ShiftScreen.route) {
                val shiftViewModel: ShiftViewModel = hiltViewModel()
                val state by shiftViewModel.state.collectAsStateWithLifecycle()
                ShiftScreen(
                    state = state,
                    channel = shiftViewModel.channel,
                    action = shiftViewModel::onAction,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            animatedComposable(route = Route.MyProfileScreen.route) {
                Log.d("viewmodel_home", "composable my profile")
                MyProfileScreen(state = sharedViewModel.state, onBack = {
                    navController.popBackStack()
                })
            }
            animatedComposable(route = Route.SettingScreen.route) {
                SettingScreen(onBack = {
                    navController.popBackStack()
                })
            }
            animatedComposable(route = Route.TermConditionScreen.route) {
                TermConditionScreen(onBack = {
                    navController.popBackStack()
                })
            }
            animatedComposable(route = Route.PrivacyScreen.route) {
                PrivacyPolicyScreen(onBack = {
                    navController.popBackStack()
                })
            }
        }
    }
}

