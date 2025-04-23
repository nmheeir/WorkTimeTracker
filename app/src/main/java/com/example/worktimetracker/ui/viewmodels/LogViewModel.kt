package com.example.worktimetracker.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.data.network.handleException
import com.example.worktimetracker.core.presentation.util.TokenKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.get
import com.example.worktimetracker.data.remote.request.CreateLogRequest
import com.example.worktimetracker.domain.use_case.log.LogUseCase
import com.example.worktimetracker.domain.use_case.shift.ShiftUseCase
import com.example.worktimetracker.ui.screens.log.LogUiAction
import com.example.worktimetracker.ui.screens.log.LogUiEvent
import com.example.worktimetracker.ui.screens.log.LogUiState
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class LogViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val logUseCase: LogUseCase,
    private val shiftUseCase: ShiftUseCase
) : ViewModel() {

    private val token = context.dataStore.get(TokenKey, "")

    val isLoading = MutableStateFlow(false)

    private val _state = MutableStateFlow(LogUiState())
    val state = _state
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LogUiState())


    private val _channel = Channel<LogUiEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        isLoading.value = true
        viewModelScope.launch {
            getLogs()
            getMyShift()
            isLoading.value = false
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

            is LogUiAction.OnSelectedShiftChange -> {
                _state.update {
                    it.copy(
                        selectedShift = action.value
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
        viewModelScope.launch {
            logUseCase.createLog(
                log = CreateLogRequest(
                    checkTime = LocalDateTime.of(_state.value.date, _state.value.time),
                    type = _state.value.type.ordinal,
                    reason = _state.value.reason,
                    userId = 0,
                    shiftId = _state.value.selectedShift!!.id
                ),
                token = token
            )
                .suspendOnSuccess {
                    _channel.send(LogUiEvent.CreateLogSuccess)
                }
                .suspendOnError {
                    _channel.send(LogUiEvent.Failure(this.toString()))
                    Timber.d("Failure $this")
                }
                .suspendOnException {
                    _channel.send(LogUiEvent.Failure(this.toString()))
                    Timber.d("Exception: $this")
                }
        }
    }

    private fun getMyShift() {
        viewModelScope.launch {
            shiftUseCase.getMyShift(
                token = token,
                start = LocalDateTime.now().minusDays(7).with(LocalTime.MIN),
                end = LocalDateTime.now().with(LocalTime.MAX)
            ).suspendOnSuccess {
                _state.update {
                    it.copy(
                        shifts = this.data.data ?: emptyList()
                    )
                }
            }
                .suspendOnFailure {
                    _channel.send(LogUiEvent.Failure(this.message()))
                }
                .suspendOnException {
                    _channel.send(LogUiEvent.Failure(this.message()))
                }
        }
    }

}