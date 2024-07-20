package com.example.worktimetracker.ui.screens.log

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.domain.use_case.log.LogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogViewModel @Inject constructor(
    private val logUseCase: LogUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {

    var state by mutableStateOf(LogUiState())

    init {
        android.util.Log.d("viewmodel_log", "init")
        getLogs()
    }

    fun onEvent(event: LogUiEvent) {
        when (event) {
            LogUiEvent.CreateLog -> {
                createLog()
            }

            LogUiEvent.GetLogs -> {
                getLogs()
            }
        }
    }

    private fun getLogs() {
        viewModelScope.launch {
            android.util.Log.d("viewmodel_log", "getLogs")
            val token = localUserManager.readAccessToken()
            when (val result: ApiResult<DataResponse<List<Log>>> = logUseCase.getLogs(token)) {
                is ApiResult.Success -> {
                    if (result.response._data != null) {
                        android.util.Log.d("viewmodel_log", result.response._data.toString())
                        state = state.copy(
                            listLog = result.response._data
                        )
                    }
                }

                is ApiResult.Error -> {
                    android.util.Log.d("viewmodel_log", "Error " + result.response._message)
                }

                is ApiResult.NetworkError -> {
                    // TODO: Handle network error
                    android.util.Log.d("viewmodel_log", "Network error" + result.message)
                }
            }

        }
    }

    private fun createLog() {
        // TODO: chưa làm
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()
        }
    }

}