package com.example.worktimetracker.ui.screens.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.data.network.handleException
import com.example.worktimetracker.data.local.db.dao.UserSessionDao
import com.example.worktimetracker.data.local.db.entity.UserSession
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.use_case.login.AuthUseCase
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.message
import com.skydoves.sandwich.retrofit.errorBody
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
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LoginUiState())

    private val channel = Channel<LoginUiEvent>()
    val loginUiEvent = channel.receiveAsFlow()

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
            is LoginUiAction.UpdateError -> {
                _state.update {
                    it.copy(
                        error = action.error,
                        isError = true
                    )
                }
            }

            is LoginUiAction.OnRememberLogin -> {
                _state.update {
                    it.copy(
                        rememberLogin = action.isRemember
                    )
                }
                Log.d("Login", "onAction: OnRememberLogin" + _state.value.rememberLogin)
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

                       channel.send(LoginUiEvent.Success)
                   }
               }
               .suspendOnError {
                   when (this.statusCode) {
                       StatusCode.BadRequest -> {
                           val error = this.errorBody?.string()
                           error.let {
                               Log.d("Login", "LoginScreen BadRequest: $it")
                           }
                           channel.send(LoginUiEvent.WrongPassword("Wrong password"))
                       }

                       StatusCode.NotFound -> {
                           channel.send(LoginUiEvent.UserNotFound("User not found"))
                       }

                       else -> {
                           Log.d("Login", "Login error else: " + this.statusCode + this.message())
//                            channel.send(LoginUiEvent.UserNotFound("User not found"))
                       }
                   }
               }
               .suspendOnException {
                   Log.d("Login", "loginexception: " + this.message)

                   Log.d("Login", "loginexception: " + this.throwable.toString())
                   channel.send(LoginUiEvent.Failure(handleException(this.throwable).showMessage()))
               }


       }
    }
}