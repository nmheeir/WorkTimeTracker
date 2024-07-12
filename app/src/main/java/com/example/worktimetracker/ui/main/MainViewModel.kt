package com.example.worktimetracker.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.domain.use_case.app_entry.AppEntryUseCase
import com.example.worktimetracker.ui.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    appEntryUseCase: AppEntryUseCase
) : ViewModel() {
    // TODO: thêm splash screen
//     val splashCondition = mutableStateOf(true)

    val startDestination = mutableStateOf(Route.OnboardingScreen.route)

    init {
        appEntryUseCase.readAppEntry().onEach { shouldStartFromAuthScreen ->
            if (shouldStartFromAuthScreen) {
                startDestination.value = Route.AuthNavigator.route
            } else {
                startDestination.value = Route.OnboardingScreen.route
            }
//            splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}