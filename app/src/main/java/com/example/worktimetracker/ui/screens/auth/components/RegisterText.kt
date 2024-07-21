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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.auth.login.LoginUiState
import com.example.worktimetracker.ui.screens.auth.register.RegisterUiState
import com.example.worktimetracker.ui.theme.Typography

@Composable
fun RegisterTextField(
    label: String,
    stateValue: String,
    stateError: String?,
    hint: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = Typography.labelLarge
        )
        OutlinedTextField(
            value = stateValue,
            onValueChange = { value ->
                onValueChange(value)
            },
            placeholder = {
                Text(text = hint)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.blue),
                unfocusedPlaceholderColor = colorResource(id = R.color.light_gray)
            ),
            keyboardOptions = keyboardOptions,
            singleLine = true,
            supportingText = {
                if (stateError != null) {
                    Text(text = stateError)
                }
            },
            shape = RoundedCornerShape(8.dp),
            isError = stateError != null,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Composable
fun RegisterPasswordTextField(
    label: String,
    stateValue: String,
    stateError: String?,
    hint: String,
    onValueChange: (String) -> Unit,
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

            )
        OutlinedTextField(
            value = stateValue,
            onValueChange = { newPassword ->
                onValueChange(newPassword)
            },
            placeholder = {
                Text(text = hint)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.blue),
                focusedPlaceholderColor = Color.LightGray,
                unfocusedPlaceholderColor = colorResource(id = R.color.light_gray)
            ),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            singleLine = true,
            supportingText = {
                if (stateError != null) {
                    Text(text = stateError)
                }
            },
            isError = stateError != null,
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