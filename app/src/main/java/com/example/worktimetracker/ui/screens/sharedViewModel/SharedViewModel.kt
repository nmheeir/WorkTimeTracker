package com.example.worktimetracker.ui.screens.sharedViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.use_case.user.UserUseCase
import com.example.worktimetracker.ui.util.JwtUtils
import com.example.worktimetracker.ui.util.validateEmail
import com.example.worktimetracker.ui.util.validatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {


    private val jwtUtils = JwtUtils()
    var state by mutableStateOf(SharedUiState())
        private set

    fun onEvent(event: SharedUiEvent) {
        Log.d("viewmodel_home_before_state", state.toString())
        when (event) {
            SharedUiEvent.GetUserInfo -> {
                getUser()
            }

            SharedUiEvent.Logout -> {
                logout()
            }

            SharedUiEvent.UpdateUser -> {
                updateProfile()
            }

            is SharedUiEvent.OnUpdateAddressChange -> {
                state = state.copy(
                    updateAddress = event.address
                )
            }

            is SharedUiEvent.OnUpdateEmailChange -> {
                state = state.copy(
                    updateEmail = event.email,
                    isUpdateEmailValid = validateEmail(event.email)
                )
            }

            is SharedUiEvent.OnUpdatePasswordChange -> {
                state = state.copy(
                    updatePassword = event.password,
                    isUpdatePasswordValid = validatePassword(event.password)
                )
            }

            is SharedUiEvent.UploadImage -> {
                uploadAvatar(event.avatarUrl)
            }
        }
    }

    private fun uploadAvatar(avatarUrl: String) {
//        viewModelScope.launch {
//            state = state.copy(
//                isLoading = true
//            )
//            val token = localUserManager.readAccessToken()
//
//            when (val result = userUseCase.uploadAvatar(token, avatarUrl)) {
//                is ApiResult.Success -> {
//                    state = state.copy(
//                        user = result.response._data!!
//                    )
//                    Log.d("viewmodel_home", "success: " + result.response._data)
//                }
//
//                is ApiResult.Error -> {
//                    Log.d("viewmodel_home", "error" + result.response._message)
//                }
//
//                is ApiResult.NetworkError -> {
//                    // TODO: handle network error
//                }
//            }
//            state = state.copy(
//                isLoading = false
//            )
//        }
    }

    private fun updateProfile() {
//        viewModelScope.launch {
//            state = state.copy(
//                isLoading = true
//            )
//            val token = localUserManager.readAccessToken()
//            val updateUser = UserUpdateRequest(
//                address = state.updateAddress,
//                email = state.updateEmail,
//                password = state.updatePassword
//            )
//
//            when (val result = userUseCase.updateUserProfile(token, updateUser)) {
//                is ApiResult.Success -> {
//                    state = state.copy(
//                        user = result.response._data!!
//                    )
//                    Log.d("viewmodel_home", "success: " + result.response._data)
//                }
//
//                is ApiResult.Error -> {
//                    Log.d("viewmodel_home", "error" + result.response._message)
//                }
//
//                is ApiResult.NetworkError -> {
//                    // TODO: handle network error
//                }
//            }
//            state = state.copy(
//                isLoading = false
//            )
//        }
    }

    private fun logout() {
        viewModelScope.launch {
            state = SharedUiState()
            localUserManager.clear()
        }
    }

    private fun getUser() {
//        viewModelScope.launch {
//            val token = localUserManager.readAccessToken()
//            if (token.isEmpty()) {
//                return@launch
//            }
//            val username = jwtUtils.extractUsername(token)
//            Log.d("viewmodel_home", username)
//
//            val result: ApiResult<DataResponse<User>> = userUseCase.getUserByUserName(username)
//
//            when (result) {
//                is ApiResult.Success -> {
//                    state = state.copy(
//                        user = result.response._data!!,
//                        updateAddress = result.response._data.address,
//                        updateEmail = result.response._data.email,
//                    )
//                }
//
//                is ApiResult.Error -> {
//                    Log.d("viewmodel_home", "error" + result.response._message)
//                }
//
//                is ApiResult.NetworkError -> {
//                    // TODO: handle network error
//                }
//            }
//
//            Log.d("viewmodel_home", result.toString())
//        }
    }
}