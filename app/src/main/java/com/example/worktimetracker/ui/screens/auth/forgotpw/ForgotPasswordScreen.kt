package com.example.worktimetracker.ui.screens.auth.forgotpw

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.worktimetracker.R

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Text(text = stringResource(id = R.string.forgot_password))
}