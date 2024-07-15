package com.example.worktimetracker.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.domain.use_case.user.UserUseCase
import com.example.worktimetracker.ui.util.JwtUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {

    private val jwtUtils = JwtUtils()
    var state by mutableStateOf(HomeUiState())
        private set

    fun onEvent(event: HomeUiEvent) {
        Log.d("viewmodel_home_before_state", state.toString())
        when (event) {
            HomeUiEvent.GetUserInfo -> {
                getUser()
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()
            val username = jwtUtils.extractUsername(token!!)
            Log.d("viewmodel_home", username)

            val result: ApiResult<DataResponse<User>> = userUseCase.getUserByUserName(username)

            when (result) {
                is ApiResult.Success -> {
                    state = state.copy(
                        user = result.response._data!!
                    )
                }

                is ApiResult.Error -> {
                    Log.d("viewmodel_home", "error" + result.response._message)
                }

                is ApiResult.NetworkError -> {
                    // TODO: handle network error
                }
            }

            Log.d("viewmodel_home", result.toString())
        }


    }

}