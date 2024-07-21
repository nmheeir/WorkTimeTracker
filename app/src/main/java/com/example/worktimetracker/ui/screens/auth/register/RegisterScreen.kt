package com.example.worktimetracker.ui.screens.auth.register

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.screens.auth.components.LoginButton
import com.example.worktimetracker.ui.screens.auth.components.RegisterPasswordTextField
import com.example.worktimetracker.ui.screens.auth.components.RegisterTextField
import com.example.worktimetracker.ui.screens.auth.login.LoginContent
import com.example.worktimetracker.ui.screens.auth.login.LoginUiEvent
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.theme.poppinsFontFamily
import com.example.worktimetracker.ui.util.BASE_LOG
import com.example.worktimetracker.ui.util.rememberImeState

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegisterSuccess : (Route) -> Unit,
    onNavigateTo: (Route) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.registerUiEvent.collect {
            when(it) {
                is ApiResult.Success -> {
                    onRegisterSuccess(Route.LoginScreen)
                }

                is ApiResult.Error -> {
                    Log.d("${BASE_LOG}_register_error", it.response._message)
                    Toast.makeText(context, it.response._message, Toast.LENGTH_SHORT).show()
                }
                is ApiResult.NetworkError -> {
                    //nothing
                }
            }
        }
    }

    RegisterContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterTopSection(modifier: Modifier = Modifier) {
    val title = buildAnnotatedString {
        append(stringResource(id = R.string.register_title))
        append(" ")
        pushStyle(SpanStyle(color = colorResource(id = R.color.blue)))
        append(stringResource(id = R.string.app_name))
    }

    val imeState by rememberImeState()

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {

        AnimatedVisibility(visible = !imeState, enter = fadeIn(), exit = fadeOut()) {
            // TODO: đổi avatar thành logo
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )
        }

        Text(
            text = title, style = Typography.displaySmall, maxLines = 2
        )
        Text(
            text = stringResource(id = R.string.register_desc),
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            style = Typography.titleMedium
        )
    }
}

@Composable
fun RegisterContent(
    modifier: Modifier = Modifier,
    state: RegisterUiState,
    onEvent: (RegisterUiEvent) -> Unit,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        RegisterTopSection()

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RegisterTextField(
                label = stringResource(id = R.string.username),
                stateValue = state.username,
                stateError = state.usernameError,
                hint = stringResource(id = R.string.username_hint),
                onValueChange = {
                    onEvent(RegisterUiEvent.UsernameChange(it))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                )
            )

            RegisterTextField(
                label = stringResource(id = R.string.email),
                stateValue = state.email,
                stateError = state.emailError,
                hint = stringResource(id = R.string.email_hint),
                onValueChange = {
                    onEvent(RegisterUiEvent.EmailChange(it))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                )
            )

            RegisterPasswordTextField(label = stringResource(id = R.string.password),
                stateValue = state.password,
                stateError = state.passwordError,
                hint = stringResource(id = R.string.password_hint),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password,
                ),
                onValueChange = {
                    onEvent(RegisterUiEvent.PasswordChange(it))
                })
            RegisterPasswordTextField(label = stringResource(id = R.string.password_confirm),
                stateValue = state.passwordConfirm,
                stateError = state.passwordConfirmError,
                hint = stringResource(id = R.string.password_hint),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password,
                ),
                onValueChange = {
                    onEvent(RegisterUiEvent.PasswordConfirmChange(it))
                })
        }

        LoginButton(text = stringResource(id = R.string.register), onClick = {
            onEvent(RegisterUiEvent.Register)
        })
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginContentPreview() {
    RegisterContent(state = RegisterUiState(
        username = "test", password = "test", email = "test@gmail.com"
    ), onEvent = {})
}