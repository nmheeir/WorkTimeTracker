package com.example.worktimetracker.ui.screens.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worktimetracker.R

@Composable
fun AuthTextField(
    label: String,
    text: String,
    hint: String,
    onChangeValue: (String) -> Unit,
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
            value = text,
            onValueChange = {
                onChangeValue(it)
            },
            placeholder = {
                Text(text = hint)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedPlaceholderColor = Color.LightGray,
                unfocusedPlaceholderColor = colorResource(id = R.color.light_gray)
            ),
            keyboardOptions = keyboardOptions,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthTextFieldPreview() {
    var text by remember {
        mutableStateOf("")
    }
    AuthTextField(
        label = "Username",
        text = text,
        hint = "Enter your username",
        onChangeValue = {
            text = it
        },
        keyboardOptions = KeyboardOptions.Default,
        modifier = Modifier.fillMaxWidth()
    )
}