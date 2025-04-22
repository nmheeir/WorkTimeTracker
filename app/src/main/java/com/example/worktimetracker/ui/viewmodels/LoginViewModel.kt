package com.example.worktimetracker.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.R
import com.example.worktimetracker.core.data.network.handleException
import com.example.worktimetracker.core.presentation.util.DeviceTokenKey
import com.example.worktimetracker.core.presentation.util.TokenKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.get
import com.example.worktimetracker.core.presentation.util.set
import com.example.worktimetracker.data.local.db.dao.UserSessionDao
import com.example.worktimetracker.data.local.db.entity.UserSession
import com.example.worktimetracker.domain.use_case.auth.AuthUseCase
import com.example.worktimetracker.domain.use_case.user.UserUseCase
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.message
import com.skydoves.sandwich.retrofit.statusCode
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authUseCase: AuthUseCase,
    private val userSessionDao: UserSessionDao,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val token = context.dataStore.get(TokenKey, "")

    val state = MutableStateFlow(LoginUiState())

    private val _channel = Channel<LoginUiEvent>()
    val channel = _channel.receiveAsFlow()

    fun onAction(action: LoginUiAction) {
        when (action) {
            is LoginUiAction.Login -> {
                login()
            }

            is LoginUiAction.OnPasswordChange -> {
                state.update {
                    it.copy(
                        password = action.password
                    )
                }
            }

            is LoginUiAction.OnUsernameChange -> {
                state.update {
                    it.copy(
                        username = action.username
                    )
                }
            }

            is LoginUiAction.OnRememberLogin -> {
                state.update {
                    it.copy(
                        rememberLogin = action.isRemember
                    )
                }
            }

        }
    }

    init {
        checkTokenExpire()
    }

    private fun login() {
        viewModelScope.launch {
            val deviceToken = context.dataStore.get(DeviceTokenKey, "")

            authUseCase
                .login(state.value.username, state.value.password, deviceToken)
                .suspendOnSuccess {
                    val token = this.data.data?.token
                    if (token != null) {
                        context.dataStore.set(TokenKey, token)

                        if (state.value.rememberLogin) {
                            userSessionDao.insertUserSession(
                                UserSession(
                                    username = state.value.username,
                                    password = state.value.password,
                                    avatarUrl = ""
                                )
                            )
                        }

                        _channel.send(LoginUiEvent.Success)
                    }
                }
                .suspendOnError {
                    state.update {
                        it.copy(
                            isError = true
                        )
                    }
                    when (this.statusCode) {
                        StatusCode.BadRequest -> {
                            state.update {
                                it.copy(
                                    error = R.string.password_wrong
                                )
                            }
                            _channel.send(LoginUiEvent.WrongPassword("Wrong password"))
                        }

                        StatusCode.NotFound -> {
                            state.update {
                                it.copy(
                                    error = R.string.username_not_found
                                )
                            }
                            _channel.send(LoginUiEvent.UserNotFound("User not found"))
                        }

                        else -> {
                            state.update {
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

    private fun checkTokenExpire() {
        viewModelScope.launch {
            userUseCase.getUserProfile(token)
                .suspendOnSuccess {
                    _channel.send(LoginUiEvent.Success)
                }
                .suspendOnError {
                    _channel.send(LoginUiEvent.Failure(this.message()))
                    Timber.d(this.message())
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