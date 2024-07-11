package com.example.worktimetracker.ui.screens.auth.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.domain.repository.remote.AuthRepository
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.ui.util.BASE_LOG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(LoginUiState())
    private val loginUiEventChannel = Channel<ApiResult<DataResponse<Token>>>()
    val loginUiEvent = loginUiEventChannel.receiveAsFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.UsernameChange -> {
                val isUsernameEmpty = event.value.isEmpty()
                val usernameError = if (isUsernameEmpty) "Username can't be empty" else null
                state = state.copy(
                    username = event.value,
                    usernameError = usernameError
                )
            }

            is LoginUiEvent.PasswordChange -> {
                val isPasswordEmpty = event.value.isEmpty()
                val passwordError = if (isPasswordEmpty) "Password can't be empty" else null
                state = state.copy(
                    password = event.value,
                    passwordError = passwordError
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
        Log.d("$BASE_LOG login viewmodel", "Login ViewModel, login function called")
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            val result = authRepository.login(
                username = state.username,
                password = state.password
            )
            loginUiEventChannel.send(result)
            state = state.copy(
                isLoading = false
            )
        }
    }
}