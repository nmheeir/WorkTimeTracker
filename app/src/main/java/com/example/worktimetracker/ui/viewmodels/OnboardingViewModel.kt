package com.example.worktimetracker.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.presentation.util.AppEntryKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.set
import com.example.worktimetracker.domain.use_case.app_entry.AppEntryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    fun onEvent(event: OnboardingUiEvent) {
        when (event) {
            OnboardingUiEvent.SaveAppEntry -> {
                saveAppEntry()
            }
        }
    }

    private fun saveAppEntry() {
        viewModelScope.launch {
            context.dataStore.set(AppEntryKey, false)
        }
    }
}

sealed class OnboardingUiEvent {
    data object SaveAppEntry : OnboardingUiEvent()
}