package com.example.worktimetracker.ui.screens.sharedViewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.data.network.handleException
import com.example.worktimetracker.data.remote.request.UserUpdateRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.use_case.user.UserUseCase
import com.example.worktimetracker.ui.util.JwtUtils
import com.example.worktimetracker.ui.util.isValidEmail
import com.example.worktimetracker.ui.util.validatePassword
import com.skydoves.sandwich.retrofit.errorBody
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {

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

            is SharedUiEvent.UploadImage -> {
                uploadAvatar(event.avatarUrl)
            }
        }
    }

    private fun uploadAvatar(avatarUrl: String) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            val token = localUserManager.readAccessToken()

            userUseCase.uploadAvatar(token, avatarUrl)
                .suspendOnSuccess {
                    state = state.copy(
                        user = this.data.data!!
                    )
                    Log.d("viewmodel_home", "success: " + this.data.data!!)
                }
                .suspendOnError {
                    Log.d("viewmodel_home", "error" + this.errorBody.toString())
                }
                .suspendOnException {
                    Log.d(
                        TAG,
                        "SharedViewModel exception: " + handleException(this.throwable).showMessage()
                    )
                }
            state = state.copy(
                isLoading = false
            )
        }
    }

    private fun logout() {
        viewModelScope.launch {
            state = SharedUiState()
            localUserManager.clear()
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()
            if (token.isEmpty()) {
                return@launch
            }
            val username = JwtUtils.extractUsername(token)
            Log.d("viewmodel_home", username)

            userUseCase.getUserByUserName(username)
                .suspendOnSuccess {
                    state = state.copy(
                        user = this.data.data!!,
                        updateAddress = this.data.data!!.address,
                        updateEmail = this.data.data!!.email,
                    )
                }
                .suspendOnError {
                    Log.d("viewmodel_home", "error" + this.errorBody.toString())
                }
                .suspendOnException {
                    Log.d(
                        TAG,
                        "SharedViewModel exception: " + handleException(this.throwable).showMessage()
                    )
                }
        }
    }
}