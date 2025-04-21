package com.example.worktimetracker.ui.screens.auth

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.worktimetracker.R
import com.example.worktimetracker.core.presentation.util.ObserveAsEvents
import com.example.worktimetracker.ui.component.background.LinearBackground
import com.example.worktimetracker.ui.navigation.Screens
import com.example.worktimetracker.ui.screens.auth.components.LoginButton
import com.example.worktimetracker.ui.screens.auth.components.LoginPasswordTextField
import com.example.worktimetracker.ui.screens.auth.components.LoginTextField
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.theme.WorkTimeTrackerTheme
import com.example.worktimetracker.ui.theme.poppinsFontFamily
import com.example.worktimetracker.ui.util.rememberImeState
import com.example.worktimetracker.ui.viewmodels.LoginUiAction
import com.example.worktimetracker.ui.viewmodels.LoginUiEvent
import com.example.worktimetracker.ui.viewmodels.LoginUiState
import com.example.worktimetracker.ui.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    onNavigateTo: (Screens) -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    ObserveAsEvents(viewModel.channel) {
        when (it) {
            LoginUiEvent.Success -> {
                onLoginSuccess()
            }

            is LoginUiEvent.UserNotFound -> {
            }

            is LoginUiEvent.WrongPassword -> {
            }

            is LoginUiEvent.Failure -> {
                Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    LinearBackground {
        LoginContent(
            state = state,
            action = viewModel::onAction,
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
    action: (LoginUiAction) -> Unit,
    onNavigateTo: (Screens) -> Unit
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
                        action(LoginUiAction.OnUsernameChange(it))
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
                        action(LoginUiAction.OnPasswordChange(it))
                    }
                )

                if (state.isError) {
                    Text(
                        text = "*" + stringResource(state.error),
                        color = Color.Red
                    )
                }


                Text(
                    text = stringResource(id = R.string.forgot_password).plus(" ?"),
                    style = Typography.titleMedium,
                    color = AppTheme.colors.onBackground,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.End)
                        .clickable {
                            onNavigateTo(Screens.ForgotPasswordScreen)
                        }
                )



                LoginButton(
                    text = stringResource(id = R.string.login),
                    onClick = {
                        action(LoginUiAction.Login)
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
            LoginContent(
                state = LoginUiState(
                    username = "test", password = "test"
                ), action = {}, onNavigateTo = {})
        }
    }
}
