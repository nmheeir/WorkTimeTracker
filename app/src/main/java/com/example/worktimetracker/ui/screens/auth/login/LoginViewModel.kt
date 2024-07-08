package com.example.worktimetracker.ui.screens.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _loginSuccess = MutableLiveData<Boolean>()

    val loginSuccess : LiveData<Boolean>
        get() = _loginSuccess

    fun login(username: String, password: String) {
        _loginSuccess.value = username == "admin" && password == "admin"
    }
}