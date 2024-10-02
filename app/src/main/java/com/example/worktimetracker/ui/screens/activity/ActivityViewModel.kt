package com.example.worktimetracker.ui.screens.activity

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
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.now
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private var checkUseCase : CheckUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel(){
    var state by mutableStateOf(ActivityUiState())
    init {
        getThisMonthChecks()
    }

    fun onEvent(event: ActivityUiEvent) {
        when(event) {
            is ActivityUiEvent.GetChecks -> {
                getChecks()
            }

            is ActivityUiEvent.OnFromDateChange -> {
                state = state.copy(
                    fromDate = event.value,
                    isLoaded = false
                )
                getChecks()
            }
            is ActivityUiEvent.OnToDateChange -> {
                state = state.copy(
                    toDate = event.value,
                    isLoaded = false
                )
                getChecks()
            }
        }
    }

    private fun getThisMonthChecks() {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()

            when (val result: ApiResult<DataResponse<List<Check>>> = checkUseCase.getCheckWithDate(token,
                month = LocalDate.now().monthNumber,
            )) {
                is ApiResult.Success -> {
                    if (result.response._data != null) {
                        android.util.Log.d("viewmodel_check", result.response._data.toString())
                        state = state.copy(
                            checkList = result.response._data,
                            isLoaded = true
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

    private fun getChecks() {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()

            when (val result: ApiResult<DataResponse<List<Check>>> = checkUseCase.getCheckWithUnixEpoch(token, Helper.localDateToEpochMillisStart(state.fromDate), Helper.localDateToEpochMillisEnd(state.toDate))) {
                is ApiResult.Success -> {
                    if (result.response._data != null) {
                        android.util.Log.d("viewmodel_check", result.response._data.toString())
                        state = state.copy(
                            checkList = result.response._data,
                            isLoaded = true
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