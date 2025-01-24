package com.example.worktimetracker.ui.screens.salary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.data.network.handleException
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.use_case.summary.SummaryUseCase
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalaryViewModel @Inject constructor(
    private val summaryUseCase: SummaryUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {

    private val _state = MutableStateFlow(SalaryState())
    val state = _state
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SalaryState())

    private val _channel = Channel<SalaryUiEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        getMyPayCheck()
    }

    private fun getMyPayCheck() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val token = localUserManager.readAccessToken()

           summaryUseCase.getMyPayCheck(token)
            .suspendOnSuccess {
            _state.update {
                it.copy(
                    listPayCheck = this.data.data!!
                )
            }

            _channel.send(SalaryUiEvent.Success)
        }

            .suspendOnError {
                _channel.send(SalaryUiEvent.Failure(this.message()))
            }
            .suspendOnException {
                _channel.send(SalaryUiEvent.Failure(handleException(this.throwable).showMessage()))
            }
            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

}