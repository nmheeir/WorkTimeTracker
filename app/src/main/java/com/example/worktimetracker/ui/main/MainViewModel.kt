package com.example.worktimetracker.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.presentation.util.AppEntryKey
import com.example.worktimetracker.core.presentation.util.TokenKey
import com.example.worktimetracker.core.presentation.util.UsernameKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.delete
import com.example.worktimetracker.core.presentation.util.get
import com.example.worktimetracker.core.presentation.util.set
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.use_case.app_entry.AppEntryUseCase
import com.example.worktimetracker.domain.use_case.user.UserUseCase
import com.example.worktimetracker.ui.navigation.Screens
import com.skydoves.sandwich.retrofit.apiMessage
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.text.set

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appEntryUseCase: AppEntryUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val token = context.dataStore[TokenKey]

    val splashCondition = MutableStateFlow(false)

    val startDestination = MutableStateFlow<String>(Screens.LoginScreen.route)

    init {
        splashCondition.value = true
        viewModelScope.launch {
            val shouldShowOnboarding = context.dataStore.get(AppEntryKey, true)
            if (shouldShowOnboarding) {
                startDestination.value = Screens.OnboardingScreen.route
                Timber.d(startDestination.value)
                splashCondition.value = false
                return@launch
            }
            checkToken()
            Timber.d(startDestination.value)
            splashCondition.value = false
        }
    }

    private suspend fun checkToken() {

        if (token.isNullOrEmpty()) {
            startDestination.value = Screens.LoginScreen.route
            return
        }

        userUseCase.getUserProfile(token)
            .suspendOnSuccess {
                Timber.d(this.data.data.toString())
                startDestination.value = Screens.HomeScreen.route
                context.dataStore.set(UsernameKey, this.data.data!!.userName)
            }
            .suspendOnFailure {
                startDestination.value = Screens.LoginScreen.route
                context.dataStore.delete(TokenKey)
                Timber.d(this.apiMessage)
            }
            .suspendOnException {
                startDestination.value = Screens.LoginScreen.route
                context.dataStore.delete(TokenKey)
                Timber.d(this.apiMessage)
            }
    }
}