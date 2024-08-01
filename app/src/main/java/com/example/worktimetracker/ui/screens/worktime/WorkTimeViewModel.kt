package com.example.worktimetracker.ui.screens.worktime

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.domain.use_case.work_time.WorkTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkTimeViewModel @Inject constructor(
    private val workTimeUseCase: WorkTimeUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {

    var state by mutableStateOf(WorkTimeUiState())

    init {
        getWorkTime()
    }

    private fun getWorkTime() {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()

            val result = workTimeUseCase.getWorkTimeEachDay(token, state.startTime, state.endTime)

            when (result) {
                is ApiResult.Success -> {
                    if (result.response._data != null) {
                        state = state.copy(
                            chartData = result.response._data
                        )
                    }
                }

                is ApiResult.Error -> {
                    Log.d("WorkTimeViewModel", "getWorkTimeError: ${result.response._message}")
                }

                is ApiResult.NetworkError -> {
                    Log.d("WorkTimeViewModel", "getWorkTimeNetworkError:" + result.message)
                }
            }
        }
    }


}