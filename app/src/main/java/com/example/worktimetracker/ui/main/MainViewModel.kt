package com.example.worktimetracker.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.data.local.db.dao.UserSessionDao
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.use_case.app_entry.AppEntryUseCase
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.util.JwtUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCase: AppEntryUseCase,
    private val localUserManager: LocalUserManager,
    private val userSessionDao: UserSessionDao
) : ViewModel() {


    var splashCondition by mutableStateOf(true)
    var startDestination by mutableStateOf(Route.LoginScreen.route)

    init {
        viewModelScope.launch {
            startDestination = getStartDestination()

            delay(500)  // Chờ một khoảng thời gian (500ms)
            splashCondition = false  // Cập nhật trạng thái splash

            localUserManager.saveDeviceToken()
        }
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

    private suspend fun getStartDestination(): String {
        return when {
            localUserManager.readAccessToken().isEmpty() -> {
                // Nếu không có token, kiểm tra có session không
                if (getSessionUser()) Route.SessionScreen.route else Route.LoginScreen.route
            }

            !checkTokenExpired() -> {
                // Nếu có token và token còn hạn, điều hướng đến HomeScreen
                Route.HomeScreen.route
            }

            getSessionUser() -> {
                // Nếu có token nhưng hết hạn và có session, điều hướng đến SessionScreen
                Route.SessionScreen.route
            }

            else -> {
                // Nếu token hết hạn và không có session, điều hướng đến LoginScreen
                Route.LoginScreen.route
            }
        }
    }

    private suspend fun getSessionUser(): Boolean {
        val session = userSessionDao.getAllUserSession()
        Log.d(TAG, "getSessionUser: $session")
        return session.isNotEmpty()
    }
}