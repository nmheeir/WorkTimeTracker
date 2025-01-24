package com.example.worktimetracker.ui.screens.auth.forgotpw

sealed class ForgotPasswordUiEvent {
    data object SendRequestSuccess : ForgotPasswordUiEvent()
    data object NotFoundUser : ForgotPasswordUiEvent()
    data object UnknownError : ForgotPasswordUiEvent()

}

sealed class ForgotPasswordUiAction {
    data class OnEmailChange(val email: String) : ForgotPasswordUiAction()
    data object SendRequest : ForgotPasswordUiAction()
    data class UpdateToken(val token: String) : ForgotPasswordUiAction()

    data class OnNewPasswordChange(val newPassword: String) : ForgotPasswordUiAction()
    data class OnConfirmPasswordChange(val confirmPassword: String) : ForgotPasswordUiAction()
    data object CheckPassword : ForgotPasswordUiAction()
    data object PasswordNotMatch : ForgotPasswordUiAction()

    data object ResetNewPassword : ForgotPasswordUiAction()
}