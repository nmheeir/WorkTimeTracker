package com.example.worktimetracker.ui.screens.profile.updateProfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.profile.component.OptionTopBar
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiEvent
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiState
import com.example.worktimetracker.ui.theme.Typography

@Composable
fun UpdateProfileScreen(
    modifier: Modifier = Modifier,
    event: (SharedUiEvent) -> Unit,
    state: SharedUiState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            OptionTopBar(title = R.string.edit_profile, onBack = onBack)
        },
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    start = 12.dp,
                    end = 12.dp
                )
                .fillMaxSize()
        ) {
            UpdateProfileTextField(
                label = "Email",
                stateValue = state.updateEmail,
                hint = "New email",
                onValueChange = { newText ->
                    event(SharedUiEvent.OnUpdateEmailChange(newText))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                )
            )
            UpdateProfileTextField(
                label = "Address",
                stateValue = state.updateAddress,
                hint = "New address",
                onValueChange = { newText ->
                    event(SharedUiEvent.OnUpdateAddressChange(newText))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                )
            )
            UpdateProfileTextField(
                label = "Password",
                stateValue = state.updatePassword,
                hint = "New password",
                onValueChange = { newText ->
                    event(SharedUiEvent.OnUpdatePasswordChange(newText))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                )
            )
            Button(
                onClick = {
                    event(SharedUiEvent.UpdateUser)
                },
                enabled = state.isUpdateEmailValid && state.isUpdatePasswordValid
            ) {
                Text(text = "Update")
            }
            if (state.isLoading) {
                Text(text = "Loading...")
            }
        }
    }
}

@Composable
fun UpdateProfileTextField(
    label: String,
    stateValue: String,
    hint: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            style = Typography.labelLarge
        )
        OutlinedTextField(
            value = stateValue,
            onValueChange = { newValue ->
                onValueChange(newValue)
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
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun test() {
    UpdateProfileScreen(event = {}, state = SharedUiState()) {

    }
}