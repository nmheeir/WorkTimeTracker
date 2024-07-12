package com.example.worktimetracker.ui.screens.onboarding

sealed class OnboardingUiEvent {
    data object SaveAppEntry : OnboardingUiEvent()
}