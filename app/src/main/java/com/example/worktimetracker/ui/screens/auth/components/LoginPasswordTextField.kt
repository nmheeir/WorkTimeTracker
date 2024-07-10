package com.example.worktimetracker.ui.screens.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.auth.login.LoginUiState

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
            fontSize = 16.sp,
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = {newPassword ->
                onPasswordChange(newPassword)
            },
            placeholder = {
                Text(text = hint)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedPlaceholderColor = Color.LightGray,
                unfocusedPlaceholderColor = colorResource(id = R.color.light_gray)
            ),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            singleLine = true,
            trailingIcon = {
                val icon = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}