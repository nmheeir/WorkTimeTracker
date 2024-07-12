package com.example.worktimetracker.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.domain.use_case.app_entry.AppEntryUseCase
import com.example.worktimetracker.ui.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    appEntryUseCase: AppEntryUseCase
) : ViewModel() {

    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Route.OnboardingScreen.route)
    val startDestination: State<String> = _startDestination

    init {
        appEntryUseCase.readAppEntry().onEach { shouldStartFromAuthScreen ->
            if (shouldStartFromAuthScreen) {
                _startDestination.value = Route.AuthNavigator.route
            } else {
                _startDestination.value = Route.OnboardingScreen.route
            }
            delay(300)
            _splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}