package com.example.worktimetracker.ui.screens.auth.session

import com.example.worktimetracker.data.local.db.entity.UserSession

sealed interface SessionUiEvent {
    data object Success : SessionUiEvent
    data class Failure(val msg: String) : SessionUiEvent
}

sealed interface SessionUiAction {
    data class Login(val userSession: UserSession) : SessionUiAction
}