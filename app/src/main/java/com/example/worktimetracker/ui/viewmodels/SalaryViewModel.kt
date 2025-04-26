package com.example.worktimetracker.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.data.network.handleException
import com.example.worktimetracker.core.presentation.util.TokenKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.get
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.domain.use_case.summary.SummaryUseCase
import com.example.worktimetracker.ui.screens.salary.SalaryUiEvent
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalaryViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val summaryUseCase: SummaryUseCase,
) : ViewModel() {

    private val token = context.dataStore.get(TokenKey, "")

    val isLoading = MutableStateFlow(false)

    val payChecks = MutableStateFlow<List<PayCheck>>(emptyList())

    private val _channel = Channel<SalaryUiEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        isLoading.value = true
        viewModelScope.launch {
            getMyPayCheck()
            isLoading.value = false
        }
    }

    private fun getMyPayCheck() {
        viewModelScope.launch {
            summaryUseCase.getMyPayCheck(token)
                .suspendOnSuccess {
                    payChecks.value = this.data.data ?: emptyList()
                    _channel.send(SalaryUiEvent.Success)
                }

                .suspendOnError {
                    _channel.send(SalaryUiEvent.Failure(this.message()))
                }
                .suspendOnException {
                    _channel.send(SalaryUiEvent.Failure(handleException(this.throwable).showMessage()))
                }
        }
    }

}