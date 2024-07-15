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

    //    private val homeUiEventChannel = Channel<ApiResult<DataResponse<User>>>()
//    val homeUiEvent = homeUiEventChannel.receiveAsFlow()
    init {
        getUser()
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.GetUser -> {
                getUser()
            }
        }
    }

    private fun getUser() {
        state = state.copy(
            userName = "Loading",
            job = "Loading",
        )

        viewModelScope.launch {
            val token = localUserManager.readAccessToken()
            val username = jwtUtils.extractUsername(token!!)
            Log.d("viewmodel_home", username)

            val result: ApiResult<DataResponse<User>> = userUseCase.getUserByUserName(username)

            when (result) {
                is ApiResult.Success -> {
                    state = state.copy(
                        userName = result.response._data!!.userName,
                        job = result.response._data.email
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