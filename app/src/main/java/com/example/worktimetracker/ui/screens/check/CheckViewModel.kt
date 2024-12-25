package com.example.worktimetracker.ui.screens.check


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.data.network.handleException
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.use_case.check.CheckUseCase
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

@HiltViewModel
class CheckViewModel @Inject constructor(
    private var checkUseCase : CheckUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {

    private val _state = MutableStateFlow(CheckUiState())
    val state = _state
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CheckUiState())

    private val _channel = Channel<CheckUiEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        getTodayCheckList()
    }

    fun onAction(action: CheckUiAction) {
        when (action) {
            is CheckUiAction.CheckIn -> {
                check(0)
            }
            is CheckUiAction.CheckOut -> {
                check(1)
            }
        }
    }

    private fun check(checkType : Int) {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()
            _state.update {
                it.copy(
                    isChecking = true
                )
            }
            checkUseCase.check(checkType, token)
                .suspendOnSuccess {
                    _state.update {
                        it.copy(
                            isChecking = false
                        )
                    }

                    _channel.send(CheckUiEvent.Success)
                }

                .suspendOnError {
                    _channel.send(CheckUiEvent.Failure(this.message()))
                }
                .suspendOnException {
                    _channel.send(CheckUiEvent.Failure(handleException(this.throwable).showMessage()))
                }
        }
    }

    private fun getTodayCheckList() {
        viewModelScope.launch {
            val token = localUserManager.readAccessToken()

            _state.update {
                it.copy(
                    isTodayCheckListLoading = true
                )
            }

            checkUseCase.getCheckWithDate(
                token, LocalDate.now().year, LocalDate.now().monthNumber, LocalDate.now().dayOfMonth
            )
                .suspendOnSuccess {
                    _state.update {
                        it.copy(
                            todayCheckList = this.data.data!!,
                            isTodayCheckListLoading = false
                        )
                    }
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
