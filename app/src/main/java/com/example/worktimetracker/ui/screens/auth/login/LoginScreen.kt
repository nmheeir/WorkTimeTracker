package com.example.worktimetracker.ui.screens.auth.login

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.worktimetracker.R
import com.example.worktimetracker.data.local.LocalUserManagerImpl
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.ui.component.LinearBackground
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.screens.auth.components.LoginButton
import com.example.worktimetracker.ui.screens.auth.components.LoginPasswordTextField
import com.example.worktimetracker.ui.screens.auth.components.LoginTextField
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.theme.WorkTimeTrackerTheme
import com.example.worktimetracker.ui.theme.poppinsFontFamily
import com.example.worktimetracker.ui.util.rememberImeState

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
                    Log.d("TestLogin", it.toString())
//                    localUserManagerImpl.saveAccessToken(it.response._data!!.token)
//                    onLoginSuccess(Route.HomeScreen)
                }

                is ApiResult.Error -> {
                    Toast.makeText(context, it.response._message, Toast.LENGTH_SHORT).show()
                }

                is ApiResult.NetworkError -> {
                    //nothing
                }
            }
        }
    }
    LinearBackground {
        LoginContent(
            state = viewModel.state,
            onEvent = viewModel::onEvent,
            onNavigateTo = {
                onNavigateTo(it)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginTopSection(modifier: Modifier = Modifier) {
    val title = buildAnnotatedString {
        pushStyle(SpanStyle(color = AppTheme.colors.onBackground))
        append(stringResource(id = R.string.login_title))
        append(" ")
        pushStyle(SpanStyle(color = AppTheme.colors.onBackgroundBlue))
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
            text = stringResource(id = R.string.login_desc),
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            style = Typography.titleMedium,
            color = AppTheme.colors.onBackground
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
    val lottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lottie_loading))
    Box(modifier = Modifier.fillMaxSize()) {
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

                LoginPasswordTextField(
                    label = stringResource(id = R.string.password),
                    state = state,
                    hint = stringResource(id = R.string.password_hint),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password,
                    ),
                    onPasswordChange = {
                        onEvent(LoginUiEvent.PasswordChange(it))
                    }
                )

                Text(
                    text = stringResource(id = R.string.forgot_password).plus(" ?"),
                    style = Typography.titleMedium,
                    color = AppTheme.colors.onBackground,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.End)
                        .clickable {
                            onNavigateTo(Route.ForgotPasswordScreen)
                        }
                )



                LoginButton(
                    text = stringResource(id = R.string.login),
                    onClick = {
                        onEvent(LoginUiEvent.Login)
                    }
                )
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        colorResource(id = R.color.black).copy(alpha = 0.3f)
                    )
                    .clickable(enabled = false, onClick = {})
            ) {
                LottieAnimation(
                    composition = lottieComposition,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginContentPreview() {
    WorkTimeTrackerTheme {
        LinearBackground {
            LoginContent(state = LoginUiState(
                username = "test", password = "test"
            ), onEvent = {}, onNavigateTo = {})
        }
    }
}
