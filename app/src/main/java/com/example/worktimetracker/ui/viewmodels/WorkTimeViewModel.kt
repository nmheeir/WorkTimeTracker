package com.example.worktimetracker.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.data.network.handleException
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.use_case.summary.SummaryUseCase
import com.example.worktimetracker.ui.screens.worktime.WorkTimeUiAction
import com.example.worktimetracker.ui.screens.worktime.WorkTimeUiEvent
import com.example.worktimetracker.ui.screens.worktime.WorkTimeUiState
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class WorkTimeViewModel @Inject constructor(
    private val summaryUseCase: SummaryUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {
    private val _state = MutableStateFlow(WorkTimeUiState())
    var state = _state
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), WorkTimeUiState())

    private val _channel = Channel<WorkTimeUiEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(500)
            getWorkTime()
            getTotalWorkTime()
            getAttendanceRecord()
        }

    }

    fun onAction(action: WorkTimeUiAction) {
        when(action) {
            is WorkTimeUiAction.OnMonthChange -> {
                _state.update {
                    it.copy(
                        month = _state.value.month.plusMonths(if (action.value == 1) 1L else -1L)
                    )
                }
                _state.update {
                    it.copy(
                        startTime = _state.value.month.withDayOfMonth(1).atStartOfDay().toString(),
                        endTime = _state.value.month.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59).toString()
                    )
                }
                viewModelScope.launch {
                    getWorkTime()
                    getTotalWorkTime()
                    getAttendanceRecord()
                }
            }
        }
    }

    private fun getWorkTime() {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()

           summaryUseCase.getWorkTimeEachDay(token, _state.value.startTime, _state.value.endTime)
               .suspendOnSuccess {
                   _state.update {
                       it.copy(
                           chartData = this.data.data!!,
                           isChartDataLoading = false
                       )
                   }
                   Log.d("ShiftTest", this.data.data.toString())
                   _channel.send(WorkTimeUiEvent.Success)
               }
               .suspendOnError {
                   _channel.send(WorkTimeUiEvent.Failure(this.message()))
                   Log.d("ShiftTest", this.message())
               }
               .suspendOnException {
                   _channel.send(WorkTimeUiEvent.Failure(handleException(this.throwable).showMessage()))
                   Log.d("ShiftTest", handleException(this.throwable).showMessage())

               }
        }
    }

    private fun getTotalWorkTime() {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()
            _state.update {
                it.copy(
                    isChartDataLoading = true
                )
            }
            summaryUseCase.getTotalWorkTime(token, _state.value.startTime, _state.value.endTime)
                .suspendOnSuccess {
                    _state.update {
                        it.copy(
                            totalWorkTime = this.data.data!!
                        )
                    }
                    Log.d("ShiftTest", this.data.data.toString())
                    _channel.send(WorkTimeUiEvent.Success)

                }
                .suspendOnError {
                    _channel.send(WorkTimeUiEvent.Failure(this.message()))
                    Log.d("ShiftTest", this.message())
                }
                .suspendOnException {
                    _channel.send(WorkTimeUiEvent.Failure(handleException(this.throwable).showMessage()))
                    Log.d("ShiftTest", handleException(this.throwable).showMessage())

                }
            _state.update {
                it.copy(
                    isChartDataLoading = false
                )
            }
        }
    }
    private fun getAttendanceRecord () {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()

            summaryUseCase.getAttendanceRecord(token, _state.value.startTime, _state.value.endTime)
                .suspendOnSuccess {
                    _state.update {
                        it.copy(
                            attendanceRecord = this.data.data!!
                        )
                    }
                    Log.d("ShiftTest", this.data.data.toString())
                    _channel.send(WorkTimeUiEvent.Success)
                }
                .suspendOnError {
                    _channel.send(WorkTimeUiEvent.Failure(this.message()))
                    Log.d("ShiftTest", this.message())
                }
                .suspendOnException {
                    _channel.send(WorkTimeUiEvent.Failure(handleException(this.throwable).showMessage()))
                    Log.d("ShiftTest", handleException(this.throwable).showMessage())
                }
        }
    }
}