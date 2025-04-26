package com.example.worktimetracker.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.presentation.util.TokenKey
import com.example.worktimetracker.core.presentation.util.UsernameKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.deletes
import com.example.worktimetracker.core.presentation.util.get
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.domain.use_case.user.UserUseCase
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val token = context.dataStore.get(TokenKey, "")

    val isLoading = MutableStateFlow(false)
    val profile = MutableStateFlow<User?>(null)

    init {
        isLoading.value = true
        viewModelScope.launch {
            fetchProfile()
            isLoading.value = false
        }
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            userUseCase.getUserProfile(token)
                .suspendOnSuccess {
                    profile.value = this.data.data
                }
                .suspendOnFailure {
                    Timber.d("Failure: %s", this.message())
                }
                .suspendOnException {
                    Timber.d("Exception: %s", this.message())
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            context.dataStore.deletes(listOf(UsernameKey, TokenKey))
        }
    }
}