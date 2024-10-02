package com.example.worktimetracker.ui.screens.home.components.ActivitySection

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivitySectionViewModel @Inject constructor(
    private val checkUseCase: CheckUseCase,
    private val localUserManager: LocalUserManager

) : ViewModel() {
    var state by mutableStateOf(ActivitySectionUiState())

    init {
        android.util.Log.d("viewmodel_log", "init")
        getCheck()
    }

    private fun getCheck() {
        viewModelScope.launch {
            android.util.Log.d("viewmodel_check", "getCheck")
            val token = localUserManager.readAccessToken()

            when (val result: ApiResult<DataResponse<List<Check>>> = checkUseCase.getCheckWithDate(token)) {
                is ApiResult.Success -> {
                    if (result.response._data != null) {
                        android.util.Log.d("viewmodel_check", result.response._data.toString())
                        state = state.copy(
                            activityItems = result.response._data
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