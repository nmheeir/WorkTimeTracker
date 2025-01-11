package com.example.worktimetracker.ui.screens.log

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.data.network.handleException
import com.example.worktimetracker.data.remote.request.CreateLogRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.domain.use_case.log.LogUseCase
import com.example.worktimetracker.ui.screens.auth.login.LoginUiState
import com.example.worktimetracker.ui.screens.check.checkPage.CheckUiEvent
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@HiltViewModel
class LogViewModel @Inject constructor(
    private val logUseCase: LogUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {

    private val _state = MutableStateFlow(LogUiState())
    val state = _state
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LogUiState())


    private val _channel = Channel<LogUiEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(500)
            getLogs()
        }
    }

    fun onAction(action: LogUiAction) {
        when (action) {
            is LogUiAction.CreateLog -> {
                createLog()
            }

            is LogUiAction.GetLogs -> {
                getLogs()
            }

            is LogUiAction.OnLogTypeChange -> {
                _state.update {
                    it.copy(
                        type = action.value
                    )
                }
            }

            is LogUiAction.OnDateChange -> {
                _state.update {
                    it.copy(
                        date = action.value
                    )
                }
            }

            is LogUiAction.OnTimeChange -> {
                _state.update {
                    it.copy(
                        time = action.value
                    )
                }
            }

            is LogUiAction.OnReasonChange -> {
                _state.update {
                    it.copy(
                        reason = action.value
                    )
                }
            }
        }
    }

    private fun getLogs() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val token = localUserManager.readAccessToken()

            logUseCase.getLogs(token)
                .suspendOnSuccess {
                    _state.update {
                        it.copy(
                            listLog = this.data.data ?: emptyList()
                        )
                    }

                    _channel.send(LogUiEvent.Success)
                }
                .suspendOnError {
                    _channel.send(LogUiEvent.Failure(this.message()))
                }
                .suspendOnException {
                    _channel.send(LogUiEvent.Failure(handleException(this.throwable).showMessage()))
                }

            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun createLog() {
        // TODO: đưa phần xử lí qua bên ui để hiện thanh thông báo
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()

            logUseCase.createLog(
                log = CreateLogRequest(
                    checkTime = _state.value.date.toString() + " " + _state.value.time,
                    type = _state.value.type.ordinal,
                    reason = _state.value.reason
                ),
                token = token
            )
                .suspendOnSuccess {
                    _channel.send(LogUiEvent.Success)
                }
                .suspendOnError {
                    _channel.send(LogUiEvent.Failure(this.message()))
                }
                .suspendOnException {
                    _channel.send(LogUiEvent.Failure(handleException(this.throwable).showMessage()))
                }
        }
    }

}