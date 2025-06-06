package com.example.worktimetracker.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.data.network.handleException
import com.example.worktimetracker.core.presentation.util.TokenKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.get
import com.example.worktimetracker.data.local.db.AppDatabase
import com.example.worktimetracker.data.local.db.entity.CheckInfoEntity
import com.example.worktimetracker.data.remote.request.CheckRequest
import com.example.worktimetracker.domain.use_case.check.CheckUseCase
import com.example.worktimetracker.domain.use_case.shift.ShiftUseCase
import com.example.worktimetracker.ui.screens.check.checkPage.CheckUiAction
import com.example.worktimetracker.ui.screens.check.checkPage.CheckUiEvent
import com.example.worktimetracker.ui.screens.check.checkPage.CheckUiState
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.now
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CheckViewModel @Inject constructor(
    private val checkUseCase: CheckUseCase,
    private val shiftUseCase: ShiftUseCase,
    @ApplicationContext private val context: Context,
    private val database: AppDatabase
) : ViewModel() {

    private val token = context.dataStore.get(TokenKey, "")

    private val _state = MutableStateFlow(CheckUiState())
    val state = _state
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), CheckUiState())

    private val _channel = Channel<CheckUiEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(500) // Delay 500ms trước khi gọi getTodayShift()
            getTodayShift()
        }
    }

    fun onAction(action: CheckUiAction) {
        when (action) {
            is CheckUiAction.Check -> {
                check(action.checkType)
            }

            is CheckUiAction.ChooseShift -> {
                _state.update {
                    it.copy(
                        choosenShift = action.shift,
                    )
                }
            }

            is CheckUiAction.UpdateCurrentLocation -> {
                _state.update {
                    it.copy(
                        currentLocation = action.currentLocation
                    )
                }
            }
        }
    }

    private fun getTodayShift() {
        viewModelScope.launch {

            _state.update {
                it.copy(
                    isShiftLoading = true
                )
            }

            shiftUseCase.getShiftsByDate(
                token = token,
                day = LocalDate.Companion.now().dayOfMonth,
                month = LocalDate.Companion.now().monthNumber,
                year = LocalDate.Companion.now().year
            )
                .suspendOnSuccess {
                    _state.update {
                        it.copy(
                            todayShifts = this.data.data ?: emptyList(),
                            isShiftLoading = false,
                        )
                    }
                    Log.d("ShiftTest", this.data.data.toString())
                    _channel.send(CheckUiEvent.Success)
                }
                .suspendOnError {
                    _channel.send(CheckUiEvent.Failure(this.message()))
                    Log.d("ShiftTest", this.message())
                }
                .suspendOnException {
                    _channel.send(CheckUiEvent.Failure(handleException(this.throwable).showMessage()))
                    Log.d("ShiftTest", handleException(this.throwable).showMessage())

                }
        }
    }

    private fun check(checkType: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isChecking = true
                )
            }

            val checkRequest = CheckRequest(
                checkType = checkType,
                longitude = _state.value.currentLocation?.longitude ?: 0.0,
                latitude = _state.value.currentLocation?.latitude ?: 0.0,
                shiftId = _state.value.choosenShift?.id ?: 0
            )

            checkUseCase.check(checkRequest, token)
                .suspendOnSuccess {
                    _state.update {
                        it.copy(
                            isChecking = false,
                            choosenShift = null
                        )
                    }

                    _channel.send(CheckUiEvent.CheckSuccess)

                    getTodayShift()
                }

                .suspendOnError {
                    _channel.send(CheckUiEvent.Failure(this.message()))
                }
                .suspendOnException {
                    _channel.send(CheckUiEvent.Failure(handleException(this.throwable).showMessage()))
                }
        }
    }
}