package com.example.worktimetracker.ui.screens.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.viewmodels.LoginUiState

@Composable
fun LoginTextField(
    label: String,
    state: LoginUiState,
    hint: String,
    onUsernameChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = Typography.labelLarge,
            color = AppTheme.colors.onBackground
        )
        OutlinedTextField(
            value = state.username,
            onValueChange = { newUsername ->
                onUsernameChange(newUsername)
            },
            placeholder = {
                Text(text = hint, color = AppTheme.colors.blurredText)
            },

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppTheme.colors.focusedBorderTextField,
                unfocusedBorderColor = AppTheme.colors.unfocusedBorderTextField,
                unfocusedPlaceholderColor = AppTheme.colors.onBackground,
                focusedTextColor = AppTheme.colors.onBackground,
                unfocusedTextColor = AppTheme.colors.onBackground
            ),
            keyboardOptions = keyboardOptions,
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Composable
fun LoginPasswordTextField(
    label: String,
    state: LoginUiState,
    hint: String,
    onPasswordChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = Typography.labelLarge,
            color = AppTheme.colors.onBackground
            )
        OutlinedTextField(
            value = state.password,
            onValueChange = { newPassword ->
                onPasswordChange(newPassword)
            },
            placeholder = {
                Text(text = hint)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppTheme.colors.focusedBorderTextField,
                unfocusedBorderColor = AppTheme.colors.unfocusedBorderTextField,
                focusedPlaceholderColor = AppTheme.colors.blurredText,
                unfocusedPlaceholderColor = AppTheme.colors.blurredText,
                focusedTextColor = AppTheme.colors.onBackground,
                unfocusedTextColor = AppTheme.colors.onBackground
            ),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            singleLine = true,
            trailingIcon = {
                val icon =
                    if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible }
                )
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}