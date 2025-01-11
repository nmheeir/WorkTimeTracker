package com.example.worktimetracker.ui.screens.shift

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.data.network.handleException
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.domain.use_case.check.CheckUseCase
import com.example.worktimetracker.domain.use_case.shift.ShiftUseCase
import com.example.worktimetracker.helper.ISOFormater
import com.example.worktimetracker.ui.screens.check.checkPage.CheckUiEvent
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.now
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject
import kotlin.math.acos

@HiltViewModel
class ShiftViewModel @Inject constructor(
    private val shiftUseCase: ShiftUseCase,
    private val localUserManager: LocalUserManager,
    private val checkUseCase: CheckUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ShiftUiState())
    val state = _state
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ShiftUiState())

    private val _channel = Channel<ShiftUiEvent>()
    val channel = _channel.receiveAsFlow()

    fun onAction(event: ShiftUiAction) {
        when (event) {
            is ShiftUiAction.MonthChange -> {
                getMyShiftsInMonth(event.month.monthValue, event.month.year)
            }

            is ShiftUiAction.DatePick -> {
                _state.update {
                    it.copy(
                        datePicked = event.date.dayOfMonth
                    )
                }
            }
            // Xử lý các sự kiện khác nếu có
        }
    }
    private fun getMyShiftsInMonth (month : Int, year : Int) {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()

            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            shiftUseCase.getShiftsByDate(
                token = token,
                day = null,
                month = month,
                year = year,
                includeCheckRecord = true)
                .suspendOnSuccess {
                    val shiftList = this.data.data ?: emptyList()
                    val shiftMap : MutableMap<Int, MutableList<Shift>> = HashMap()
                    shiftList.forEach { shift ->
                        // Nếu date đã có trong shiftMap, thêm vào danh sách
                        val date = ISOFormater.fromISODateTimetoLocalDateTime(shift.start).dayOfMonth
                        if (shiftMap.containsKey((date))) {
                            shiftMap[date]?.add(shift)
                        } else {
                            // Nếu chưa có, tạo mới một danh sách chứa Shift
                            shiftMap[date] = mutableListOf(shift)
                        }
                    }
                    _state.update {
                        it.copy(
                            shiftList = shiftList,
                            shiftMap = shiftMap,
                            isLoading = false,
                        )
                    }



                    _channel.send(ShiftUiEvent.Success)
                }
                .suspendOnError {
                    _channel.send(ShiftUiEvent.Failure(this.message()))
                    Log.d("ShiftTest", this.message())
                }
                .suspendOnException {
                    _channel.send(ShiftUiEvent.Failure(handleException(this.throwable).showMessage()))
                    Log.d("ShiftTest", handleException(this.throwable).showMessage())

                }
        }
    }

}