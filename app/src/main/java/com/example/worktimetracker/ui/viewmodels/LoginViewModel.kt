package com.example.worktimetracker.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.R
import com.example.worktimetracker.core.data.network.handleException
import com.example.worktimetracker.data.local.db.dao.UserSessionDao
import com.example.worktimetracker.data.local.db.entity.UserSession
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.use_case.auth.AuthUseCase
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.retrofit.statusCode
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userSessionDao: UserSessionDao,
    private val localUserManager: LocalUserManager
) : ViewModel() {
    private val _state = MutableStateFlow(LoginUiState())
    val state = _state
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), LoginUiState())

    private val _channel = Channel<LoginUiEvent>()
    val channel = _channel.receiveAsFlow()

    fun onAction(action: LoginUiAction) {
        when (action) {
            is LoginUiAction.Login -> {
               login()
            }

            is LoginUiAction.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = action.password
                    )
                }
            }

            is LoginUiAction.OnUsernameChange -> {
                _state.update {
                    it.copy(
                        username = action.username
                    )
                }
            }

            is LoginUiAction.OnRememberLogin -> {
                _state.update {
                    it.copy(
                        rememberLogin = action.isRemember
                    )
                }
            }

        }
    }

    private fun login() {
       viewModelScope.launch {
           val deviceToken = localUserManager.readDeviceToken()

           authUseCase
               .login(_state.value.username, _state.value.password, deviceToken)
               .suspendOnSuccess {
                   val token = this.data.data?.token
                   if(token != null) {
                       localUserManager.saveAccessToken(token)

                       if (_state.value.rememberLogin) {
                           userSessionDao.insertUserSession(
                               UserSession(
                                   username = _state.value.username,
                                   password = _state.value.password,
                                   avatarUrl = ""
                               )
                           )
                       }

                       _channel.send(LoginUiEvent.Success)
                   }
               }
               .suspendOnError {
                   _state.update {
                       it.copy(
                           isError = true
                       )
                   }
                   when (this.statusCode) {
                       StatusCode.BadRequest -> {
                           _state.update {
                               it.copy(
                                   error = R.string.password_wrong
                               )
                           }
                           _channel.send(LoginUiEvent.WrongPassword("Wrong password"))
                       }

                       StatusCode.NotFound -> {
                           _state.update {
                               it.copy(
                                   error = R.string.username_not_found
                               )
                           }
                           _channel.send(LoginUiEvent.UserNotFound("User not found"))
                       }

                       else -> {
                           _state.update {
                               it.copy(
                                   error = R.string.username_not_found
                               )
                           }
                           _channel.send(LoginUiEvent.UserNotFound("User not found"))
                       }
                   }
               }
               .suspendOnException {
                   _channel.send(LoginUiEvent.Failure(handleException(this.throwable).showMessage()))
                   Log.d("LoginViewModel", this.throwable.toString())
               }


       }
    }
}

sealed interface LoginUiEvent {
    data class UserNotFound(val msg: String) : LoginUiEvent
    data class WrongPassword(val msg: String) : LoginUiEvent

    data object Success : LoginUiEvent
    data class Failure(val msg: String) : LoginUiEvent
}


sealed interface LoginUiAction {
    data class OnUsernameChange(val username: String) : LoginUiAction
    data class OnPasswordChange(val password: String) : LoginUiAction
    data object Login : LoginUiAction
    data class OnRememberLogin(val isRemember: Boolean) : LoginUiAction
}

data class LoginUiState(
    val isLoading: Boolean = false,

    val username: String = "",
    val isUsernameEmpty: Boolean = false,

    val password: String = "",
    val isPasswordEmpty: Boolean = false,

    val error: Int = 0,
    val isError: Boolean = false,

    val rememberLogin: Boolean = false
)