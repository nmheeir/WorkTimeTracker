package com.example.worktimetracker.ui.screens.check

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.domain.use_case.check.CheckUseCase
import com.example.worktimetracker.helper.Helper
import com.example.worktimetracker.ui.screens.auth.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Objects
import javax.inject.Inject

@HiltViewModel
class CheckViewModel @Inject constructor(
    private var checkUseCase : CheckUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {
    private val checkUiEventChannel = Channel<ApiResult<DataResponse<Any>>>()
    val checkUiEvent = checkUiEventChannel.receiveAsFlow()
    var state by mutableStateOf(CheckUiState())

    init {
        getTodayCheckList()
    }

    fun onEvent(event: CheckUiEvent) {
        when (event) {
            is CheckUiEvent.CheckIn -> {
                check(0)
            }
            is CheckUiEvent.CheckOut -> {
                check(1)
            }
        }
    }

    private fun check(checkType : Int) {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()
            val result = checkUseCase.check(checkType, token)
            checkUiEventChannel.send(result)
        }
    }

    private fun getTodayCheckList() {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()

            when (val result: ApiResult<DataResponse<List<Check>>> = checkUseCase.getCheckWithDate(token, Helper.getStartOfDayInMillis(), Helper.getStartOfDayInMillis() + 86400000)) {
                is ApiResult.Success -> {
                    if (result.response._data != null) {
                        android.util.Log.d("viewmodel_check", result.response._data.toString())
                        state = state.copy(
                            todayCheckList = result.response._data
                        )
                    }
                }

                is ApiResult.Error -> {
                    android.util.Log.d("viewmodel_check", "Error " + result.response._message)
                }

                is ApiResult.NetworkError -> {
                    // TODO: Handle network error
                    android.util.Log.d("viewmodel_check", "Network error" + result.message)
                }
            }
        }
    }
}