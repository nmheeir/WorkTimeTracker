package com.example.worktimetracker.ui.screens.auth.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.worktimetracker.data.manager.LocalUserManagerImpl
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.screens.auth.components.LoginButton
import com.example.worktimetracker.ui.screens.auth.components.LoginPasswordTextField
import com.example.worktimetracker.ui.screens.auth.components.LoginTextField
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.theme.poppinsFontFamily
import com.example.worktimetracker.ui.util.BASE_LOG

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: (Route) -> Unit,
    onNavigateTo: (Route) -> Unit
) {
    val context = LocalContext.current

    val localUserManagerImpl = LocalUserManagerImpl(context)

    LaunchedEffect(viewModel, context) {
        viewModel.loginUiEvent.collect {
            when (it) {
                is ApiResult.Success -> {
                    localUserManagerImpl.saveAccessToken(it.response._data.token)
                    onLoginSuccess(Route.MainNavigator)
                }

                is ApiResult.Error -> {
                    Log.d("${BASE_LOG}_login_error", it.message)
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LoginContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        onNavigateTo = {
            onNavigateTo(it)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LoginTopSection(modifier: Modifier = Modifier) {
    val title = buildAnnotatedString {
        append(stringResource(id = R.string.login_title))
        append(" ")
        pushStyle(SpanStyle(color = colorResource(id = R.color.blue)))
        append(stringResource(id = R.string.app_name))
    }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        // TODO: đổi avatar thành logo
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = title, style = Typography.displaySmall, maxLines = 2
        )
        Text(
            text = stringResource(id = R.string.login_desc),
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            style = Typography.titleMedium,

            )
    }
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    state: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
    onNavigateTo: (Route) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize()
    ) {
        LoginTopSection()

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LoginTextField(
                label = stringResource(id = R.string.username),
                state = state,
                hint = stringResource(id = R.string.username_hint),
                onUsernameChange = {
                    onEvent(LoginUiEvent.UsernameChange(it))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                )
            )

            LoginPasswordTextField(label = stringResource(id = R.string.password),
                state = state,
                hint = stringResource(id = R.string.password_hint),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password,
                ),
                onPasswordChange = {
                    onEvent(LoginUiEvent.PasswordChange(it))
                })

            Text(text = stringResource(id = R.string.forgot_password).plus(" ?"),
                style = Typography.titleMedium,
                color = colorResource(id = R.color.blue),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.End)
                    .clickable {
                        onNavigateTo(Route.ForgotPasswordScreen)
                    })

            LoginButton(text = stringResource(id = R.string.login), onClick = {
                onEvent(LoginUiEvent.Login)
            })
        }


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginContentPreview() {
    LoginContent(state = LoginUiState(
        username = "test", password = "test"
    ), onEvent = {}, onNavigateTo = {})
}
