package com.example.worktimetracker.ui.screens.auth.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.domain.use_case.login.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    var state by mutableStateOf(LoginUiState())

    private val loginUiEventChannel = Channel<ApiResult<DataResponse<Token>>>()
    val loginUiEvent = loginUiEventChannel.receiveAsFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.UsernameChange -> {
                val usernameEmpty = event.value.isEmpty()
                state = state.copy(
                    username = event.value,
                    isUsernameEmpty = usernameEmpty
                )
            }

            is LoginUiEvent.PasswordChange -> {
                val passwordEmpty = event.value.isEmpty()
                state = state.copy(
                    password = event.value,
                    isPasswordEmpty = passwordEmpty
                )
            }

            is LoginUiEvent.Login -> {
                login()
            }
            else -> {
                //do nothing
            }

        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )

            Log.d("login", "chạy logn")

            val result = authUseCase.login(
                username = state.username,
                password = state.password
            )
            loginUiEventChannel.send(result)
            Log.d("login_viewmodel", result.toString())

            state = state.copy(
                isLoading = false
            )
        }
    }
}