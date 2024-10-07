package com.example.worktimetracker.ui.screens.shift

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.domain.use_case.check.CheckUseCase
import com.example.worktimetracker.domain.use_case.shift.ShiftUseCase
import com.example.worktimetracker.helper.Helper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.now
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@HiltViewModel
class ShiftViewModel @Inject constructor(
    private val shiftUseCase: ShiftUseCase,
    private val localUserManager: LocalUserManager,
    private val checkUseCase: CheckUseCase
) : ViewModel() {
    var state by mutableStateOf(ShiftState())

    private val shiftUiEventChannel = Channel<ApiResult<DataResponse<Token>>>()
    val shiftUiEvent = shiftUiEventChannel.receiveAsFlow()

    fun onEvent(event: ShiftUiEvent) {
        when (event) {
            is ShiftUiEvent.GetMyShiftsInMonth -> {
                getMyShiftsInMonth(event.month, event.year)
            }

            is ShiftUiEvent.GetMyChecksInMonth -> {
                getCheckListInMonth(event.month, event.year)
            }
            is ShiftUiEvent.DialogToggle -> {
                dialogToggle(event.date)
            }
            // Xử lý các sự kiện khác nếu có
        }
    }
    private fun getMyShiftsInMonth (month : Int, year : Int) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val token = localUserManager.readAccessToken();
            when( val result : ApiResult<DataResponse<List<Shift>>> = shiftUseCase.getMyShiftsInMonth(month, year, token) ) {
                is ApiResult.Success -> {
                    state = state.copy(
                        shiftList = result.response._data,
                        shiftMap = result.response._data?.groupBy { it.day }
                    )
                }
                is ApiResult.Error -> {
                    android.util.Log.d("viewmodel_shift", "Error " + result.response._message)
                }

                is ApiResult.NetworkError -> {
                    // TODO: Handle network error
                    android.util.Log.d("viewmodel_shift", "Network error" + result.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }
    private fun getCheckListInMonth(month : Int, year : Int) {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()

            when (val result: ApiResult<DataResponse<List<Check>>> = checkUseCase.getCheckWithDate(token, month= month, year = year)) {
                is ApiResult.Success -> {
                    if (result.response._data != null) {
                        android.util.Log.d("viewmodel_check", result.response._data.toString())
                        state = state.copy(
                            checkList = result.response._data ,
                            checkMap = result.response._data.groupBy { it.getDate() }
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

    private fun dialogToggle(date : Int = 0) {
        viewModelScope.launch {
            state = state.copy(isDialogShow = !state.isDialogShow, dialogDate = date)
        }
    }
}