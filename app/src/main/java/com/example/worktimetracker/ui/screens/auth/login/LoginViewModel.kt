package com.example.worktimetracker.ui.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.domain.repository.remote.AuthRepository
import com.example.worktimetracker.domain.result.ApiResult
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
    private val loginUiEventChannel = Channel<ApiResult<User>>()
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

    // TODO:
    private fun login() {
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