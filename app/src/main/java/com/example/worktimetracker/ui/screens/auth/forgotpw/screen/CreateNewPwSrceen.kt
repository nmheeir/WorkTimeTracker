package com.example.worktimetracker.ui.screens.auth.forgotpw.screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.core.presentation.util.ObserveAsEvents
import com.example.worktimetracker.ui.component.dialog.SuccessDialog
import com.example.worktimetracker.ui.navigation.Screens
import com.example.worktimetracker.ui.screens.auth.forgotpw.ForgotPasswordUiAction
import com.example.worktimetracker.ui.screens.auth.forgotpw.ForgotPasswordUiState
import com.example.worktimetracker.ui.theme.Typography
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Eye
import compose.icons.fontawesomeicons.solid.EyeSlash
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewPasswordScreen(
    channel: Flow<CreateNewPasswordUiEvent>,
    modifier: Modifier = Modifier,
    action: (ForgotPasswordUiAction) -> Unit,
    state: ForgotPasswordUiState,
    onNavigateTo: (Screens) -> Unit,
    onBack: () -> Unit
) {
    Log.d(TAG, "Navigating to CreateNewPasswordScreen")

    val context = LocalContext.current

    LaunchedEffect(state.newPassword, state.confirmPassword) {
        delay(500)
        action(ForgotPasswordUiAction.CheckPassword)
    }

    var isVisible by remember  { mutableStateOf(false) }
    var dialogContent by remember  { mutableStateOf("") }
    var isSuccess by remember  { mutableStateOf(true) }
    ObserveAsEvents(channel) {
        when (it) {
            is CreateNewPasswordUiEvent.ResetPasswordFailure -> {
                isVisible = true
                isSuccess = false
                dialogContent = it.msg
            }

            CreateNewPasswordUiEvent.ResetPasswordSuccess -> {
                isVisible = true
                isSuccess = true
                dialogContent = context.getString(R.string.reset_pw_success_instruction)
            }
        }
    }
    if(isVisible) {
        SuccessDialog(isSuccess, dialogContent, { isVisible = false })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Back",
                        style = Typography.titleLarge.copy(
                            fontWeight = FontWeight.Normal
                        )
                    )
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_left),
                        contentDescription = "Icon Arrow Back",
                        modifier = Modifier
                            .clickable {

                            }
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(paddingValues)
        ) {
            CreateNewPasswordDetail(
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            PasswordInputSection(
                modifier = Modifier.padding(horizontal = 24.dp),
                state = state,
                action = {
                    action(it)
                }
            )

            Button(
                onClick = {
                    if (state.newPassword == state.confirmPassword) {
                        action(ForgotPasswordUiAction.ResetNewPassword)
                    } else {
                        action(ForgotPasswordUiAction.PasswordNotMatch)
                    }
                },
//                colors = ButtonDefaults.buttonColors(
//                    contentColor = ,
//                    containerColor = colorResource(R.color.primary)
//                ),
                shape = RoundedCornerShape(1.dp),
                enabled = state.isButtonEnabled,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Reset Password")
            }
        }
    }
}

@Composable
private fun CreateNewPasswordDetail(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.create_new_pw),
            style = Typography.labelLarge
        )
        Text(
            text = stringResource(R.string.new_pw_instruction),
            style = Typography.bodyMedium
        )
    }
}

@Composable
private fun PasswordInputSection(
    modifier: Modifier = Modifier,
    state: ForgotPasswordUiState,
    action: (ForgotPasswordUiAction) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        PasswordInputField(
            title = "New Password",
            value = {
                state.newPassword
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = {
                action(ForgotPasswordUiAction.OnNewPasswordChange(it))
            }
        )

        PasswordInputField(
            title = "Confirm Password",
            value = {
                state.confirmPassword
            },
            onValueChange = {
                action(ForgotPasswordUiAction.OnConfirmPasswordChange(it))
            }
        )
    }
}

@Composable
private fun PasswordInputField(
    modifier: Modifier = Modifier,
    title: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    value: () -> String,
    onValueChange: (String) -> Unit
) {
    var showPassword by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = title,
            style = Typography.labelLarge
        )
        OutlinedTextField(
            value = value(),
            onValueChange = {
                onValueChange(it)
            },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val iconResource =
                    if (showPassword) FontAwesomeIcons.Solid.EyeSlash else FontAwesomeIcons.Solid.Eye
                Icon(
                    imageVector = iconResource,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            showPassword = !showPassword
                        },
                )
            },
            keyboardOptions = keyboardOptions,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}