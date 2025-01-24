package com.example.worktimetracker.ui.screens.auth.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.data.network.handleException
import com.example.worktimetracker.data.local.db.dao.UserSessionDao
import com.example.worktimetracker.data.local.db.entity.UserSession
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.use_case.auth.AuthUseCase
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
class SessionViewModel @Inject constructor(
    private val userSessionDao: UserSessionDao,
    private val localUserManager: LocalUserManager,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<List<UserSession>>(emptyList())

    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val _channel = Channel<SessionUiEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val userSessions = userSessionDao.getAllUserSession()
            _state.update {
                userSessions
            }
        }
    }

    fun onAction(action: SessionUiAction) {
        when (action) {
            is SessionUiAction.Login -> {
                login(action.userSession)
            }
        }
    }

    private fun login(user: UserSession) {
        viewModelScope.launch {
            authUseCase.login(user.username, user.password, localUserManager.readDeviceToken())
                .suspendOnSuccess {
                    localUserManager.saveAccessToken(this.data.data!!.token)
                    _channel.send(SessionUiEvent.Success)
                }
                .suspendOnError {
                    _channel.send(SessionUiEvent.Failure(this.message()))
                }
                .suspendOnException {
                    _channel.send(SessionUiEvent.Failure(handleException(this.throwable).showMessage()))
                }
        }
    }

}