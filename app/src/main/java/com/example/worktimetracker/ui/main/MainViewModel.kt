package com.example.worktimetracker.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.use_case.app_entry.AppEntryUseCase
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.util.JwtUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCase: AppEntryUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {


    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Route.OnboardingScreen.route)
    val startDestination: State<String> = _startDestination

    init {
        appEntryUseCase.readAppEntry().onEach { shouldStartFromAuthScreen ->
            if (shouldStartFromAuthScreen) {
                if (!checkTokenExpired()) {
                    _startDestination.value = Route.AuthNavigator.route
                } else {
                    _startDestination.value = Route.HomeScreen.route
                }
            } else {
                _startDestination.value = Route.OnboardingScreen.route
            }
            delay(500)
            _splashCondition.value = false
            localUserManager.saveDeviceToken()
        }.launchIn(viewModelScope)
    }

    private suspend fun checkTokenExpired(): Boolean {
        val token = localUserManager.readAccessToken()
        if (token.isEmpty()) {
            return false
        }
        if (JwtUtils.isTokenExpired(token)) {
            return false
        }
        return true
    }
}