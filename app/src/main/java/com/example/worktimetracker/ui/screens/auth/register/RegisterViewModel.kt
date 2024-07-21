package com.example.worktimetracker.ui.screens.auth.register

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.domain.use_case.login.AuthUseCase
import com.example.worktimetracker.helper.Helper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    var state by mutableStateOf(RegisterUiState())

    private val registerUiEventChannel = Channel<ApiResult<DataResponse<String>>>()
    val registerUiEvent = registerUiEventChannel.receiveAsFlow()


    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.UsernameChange -> {
                val isUsernameEmpty = event.value.isEmpty()
                val isEnoughCharacter = event.value.length > 8;
                val usernameError =
                    if (isUsernameEmpty) "Username can't be empty"
                    else if (!isEnoughCharacter) "Username need atleast 8 characters."
                    else null
                state = state.copy(
                    username = event.value,
                    usernameError = usernameError
                )
            }

            is RegisterUiEvent.EmailChange -> {
                val isEmailEmpty = event.value.isEmpty()
                val emailError =
                    if (isEmailEmpty) "Email can't be empty"
                    else if (!Helper.isValidEmail(event.value)) "Email is not valid"
                    else null
                state = state.copy(
                    email = event.value,
                    emailError = emailError
                )
            }

            is RegisterUiEvent.PasswordChange -> {
                val isPasswordEmpty = event.value.isEmpty()
                val passwordError =
                    if (isPasswordEmpty) "Password can't be empty"
                    else if (!Helper.isValidPassword(event.value)) "Password need both special character and number."
                    else null
                state = state.copy(
                    password = event.value,
                    passwordError = passwordError
                )
            }

            is RegisterUiEvent.PasswordConfirmChange -> {
                val passwordError =
                    if (event.value != state.password) "Wrong password" else null



                state = state.copy(
                    passwordConfirm = event.value,
                    passwordConfirmError = passwordError
                )
            }

            is RegisterUiEvent.Register -> {
                var isVaid = true;
                if (state.email.isEmpty() || state.emailError != null) {
                    isVaid = false
                    state = state.copy(
                        emailError = "Email is required or invalid"
                    )
                }

                if (state.username.isEmpty() || state.usernameError != null) {
                    isVaid = false
                    state = state.copy(
                        usernameError = "Username is required or invalid"
                    )
                }

                if (state.password.isEmpty() || state.passwordError != null) {
                    isVaid = false
                    state = state.copy(
                        passwordError = "Password is required or invalid"
                    )
                }

                if (state.passwordConfirm.isEmpty() || state.passwordConfirmError != null) {
                    isVaid = false
                    state = state.copy(
                        passwordConfirmError = "Password confirmation is required or invalid"
                    )
                }

                if(isVaid) {
                    register()
                }
            }
            else -> {
                //do nothing
            }

        }
    }

    private fun register() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )

            Log.d("register_viewModel", "đăng kí")

            val result = authUseCase.register(
                username = state.username,
                password = state.password,
                email = state.email
            )
            registerUiEventChannel.send(result)
            Log.d("register_viewModel", result.toString())

            state = state.copy(
                isLoading = false
            )
        }
    }
}