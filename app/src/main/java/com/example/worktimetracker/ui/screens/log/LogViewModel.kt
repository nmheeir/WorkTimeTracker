package com.example.worktimetracker.ui.screens.log

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.data.remote.request.CreateLogRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.domain.use_case.log.LogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
            is LogUiEvent.CreateLog -> {
                createLog()
            }

            is LogUiEvent.GetLogs -> {
                getLogs()
            }

            is LogUiEvent.OnLogTypeChange -> {
                state = state.copy(
                    type = event.value
                )
            }

            is LogUiEvent.OnDateChange -> {
                state = state.copy(
                    date = event.value
                )
            }

            is LogUiEvent.OnTimeChange -> {
                state = state.copy(
                    time = event.value
                )
            }
        }
    }

    private fun getLogs() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = false
            )
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
            state = state.copy(
                isLoading = false
            )
        }
    }

    private fun createLog() {
        // TODO: đưa phần xử lí qua bên ui để hiện thanh thông báo
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            val token = localUserManager.readAccessToken()
            val currentTime: String =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))

            val result = logUseCase.createLog(
                log = CreateLogRequest(
                    checkTime = state.date + " " + state.time,
                    type = state.type.ordinal,
                    createAt = currentTime
                ),
                token = token
            )
            when (result) {
                is ApiResult.Success -> {
                    android.util.Log.d("viewmodel_log", result.toString())
                }

                is ApiResult.Error -> {
                    android.util.Log.d("viewmodel_log", "Error " + result.response._message)
                }
                is ApiResult.NetworkError -> {
                    // TODO: Handle network error
                    android.util.Log.d("viewmodel_log", "Network error " + result.message)
                }
            }
            state = state.copy(
                isLoading = false
            )
        }
    }

}