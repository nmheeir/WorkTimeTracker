package com.example.worktimetracker.ui.screens.check

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.domain.use_case.check.CheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Objects
import javax.inject.Inject

@HiltViewModel
class CheckViewModel @Inject constructor(
    private var checkUseCase : CheckUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {
    private val checkUiEventChannel = Channel<ApiResult<DataResponse<Any>>>()
    val checkUiEvent = checkUiEventChannel.receiveAsFlow()

    fun onEvent(event: CheckUiEvent) {
        when (event) {
            is CheckUiEvent.CheckIn -> {
                check(0)
            }
            is CheckUiEvent.CheckOut -> {
                check(1)
            }
        }
    }

    private fun check(checkType : Int) {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()
            val result = checkUseCase.check(checkType, token)
            checkUiEventChannel.send(result)
        }
    }
}