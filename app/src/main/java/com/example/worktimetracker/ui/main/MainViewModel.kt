package com.example.worktimetracker.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.presentation.util.AppEntryKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.get
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.use_case.app_entry.AppEntryUseCase
import com.example.worktimetracker.ui.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val appEntryUseCase: AppEntryUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {


    val splashCondition = MutableStateFlow(false)

    val startDestination = MutableStateFlow<String>(Screens.LoginScreen.route)

    init {
        viewModelScope.launch {
            val shouldShowOnboarding = context.dataStore.get(AppEntryKey, true)
            if (shouldShowOnboarding) {
                startDestination.value = Screens.OnboardingScreen.route
                Timber.d(startDestination.value)
                return@launch
            }
            Timber.d(startDestination.value)
            startDestination.value = Screens.LoginScreen.route
        }
    }
}