package com.example.worktimetracker.ui.screens.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.auth.login.LoginUiState

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
            fontSize = 16.sp,
        )
        OutlinedTextField(
            value = state.username,
            onValueChange = { newUsername ->
                onUsernameChange(newUsername)
            },
            placeholder = {
                Text(text = hint)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedPlaceholderColor = colorResource(id = R.color.purple_200),
                unfocusedPlaceholderColor = colorResource(id = R.color.purple_200)
            ),
            keyboardOptions = keyboardOptions,
            singleLine = true,
            supportingText = {
                if (state.usernameError != null) {
                    Text(text = state.usernameError)
                }
            },
            isError = state.usernameError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}