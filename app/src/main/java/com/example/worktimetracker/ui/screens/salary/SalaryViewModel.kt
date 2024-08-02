package com.example.worktimetracker.ui.screens.salary

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.domain.use_case.summary.SummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalaryViewModel @Inject constructor(
    private val summaryUseCase: SummaryUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {

    var state by mutableStateOf(SalaryState())

    init {
        getMyPayCheck()
    }

    private fun getMyPayCheck() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            val token = localUserManager.readAccessToken()

            when (val result = summaryUseCase.getMyPayCheck(token)) {
                is ApiResult.Error -> {
                    state = state.copy(
                        messageError = result.response._message
                    )
                    Log.d("SalaryViewModel", "error: ${result.response}")
                }

                is ApiResult.NetworkError -> {
                    state = state.copy(
                        messageError = result.message
                    )
                    Log.d("SalaryViewModel", "networkErr: ${result.message}")
                }

                is ApiResult.Success -> {
                    if (result.response._data != null) {
                        state = state.copy(
                            listPayCheck = result.response._data
                        )
                    }
                }
            }
            state = state.copy(
                isLoading = false
            )
        }
    }

}