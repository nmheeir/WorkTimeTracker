package com.example.worktimetracker.ui.screens.auth.forgotpw

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.data.network.handleException
import com.example.worktimetracker.domain.use_case.auth.AuthUseCase
import com.example.worktimetracker.ui.screens.auth.forgotpw.screen.CreateNewPasswordUiEvent
import com.example.worktimetracker.ui.util.isValidEmail
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.retrofit.errorBody
import com.skydoves.sandwich.retrofit.statusCode
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(FlowPreview::class)
@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordUiState())
    val state = _state
        .stateIn(viewModelScope, SharingStarted.Lazily, ForgotPasswordUiState())

    private val _channel = Channel<ForgotPasswordUiEvent>()
    val channel = _channel.receiveAsFlow()

    private val _channel2 = Channel<CreateNewPasswordUiEvent>()
    val channel2 = _channel2.receiveAsFlow()

    fun onAction(event: ForgotPasswordUiAction) {
        when (event) {
            is ForgotPasswordUiAction.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            ForgotPasswordUiAction.SendRequest -> {
                if (!_state.value.email.isValidEmail()) {
                    _state.update {
                        it.copy(
                            error = "Email is not valid",
                            isError = true
                        )
                    }
                } else {
                    sendRequest(_state.value.email)
                }
            }

            is ForgotPasswordUiAction.OnConfirmPasswordChange -> {
                _state.update {
                    it.copy(
                        confirmPassword = event.confirmPassword
                    )
                }
            }

            is ForgotPasswordUiAction.OnNewPasswordChange -> {
                _state.update {
                    it.copy(
                        newPassword = event.newPassword
                    )
                }
            }

            ForgotPasswordUiAction.ResetNewPassword -> {
                resetNewPassword()
            }

            ForgotPasswordUiAction.PasswordNotMatch -> {
                _state.update {
                    it.copy(
                        error = "Password not match",
                        isError = true
                    )
                }
            }

            ForgotPasswordUiAction.CheckPassword -> {
//                Log.d(TAG, "onAction: ${_state.value.newPassword} ${_state.value.confirmPassword}")
                if (_state.value.newPassword == _state.value.confirmPassword) {
                    _state.update {
                        it.copy(
                            isButtonEnabled = true
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            error = "Password do not match",
                            isError = false,
                            isButtonEnabled = false
                        )
                    }
                }
            }

            is ForgotPasswordUiAction.UpdateToken -> {
                _state.update {
                    it.copy(
                        token = event.token
                    )
                }
            }
        }
    }

    private fun resetNewPassword() {
        viewModelScope.launch {
            val token = "Bearer " + _state.value.token
            authUseCase.resetPassword(token, _state.value.newPassword)
                .suspendOnSuccess {
                    _channel2.send(CreateNewPasswordUiEvent.ResetPasswordSuccess)
                }
                .suspendOnError {
                    _channel2.send(CreateNewPasswordUiEvent.ResetPasswordFailure(this.errorBody.toString()))
                }
                .suspendOnException {
                    Log.d(
                        TAG,
                        "resetNewPassword exception: " + handleException(this.throwable).showMessage()
                    )
                }
        }
    }

    private fun sendRequest(email: String) {
        viewModelScope.launch {
            authUseCase.requestPasswordReset(email)
                .suspendOnSuccess {
                    _channel.send(ForgotPasswordUiEvent.SendRequestSuccess)
                }
                .suspendOnError {
                    when (this.statusCode) {
                        StatusCode.NotFound -> {
                            _channel.send(ForgotPasswordUiEvent.NotFoundUser)
                        }

                        else -> {
                            Log.d(TAG, "sendRequest: ${this.statusCode} + ${this.errorBody}")
                            _channel.send(ForgotPasswordUiEvent.UnknownError)
                        }
                    }
                }
                .suspendOnException {
                    Log.d(
                        TAG,
                        "sendRequest exception: " + handleException(this.throwable).showMessage()
                    )
                }
        }
    }

}