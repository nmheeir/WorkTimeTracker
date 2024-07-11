package com.example.worktimetracker.ui.screens.auth.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worktimetracker.R
import com.example.worktimetracker.data.manager.LocalUserManager
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.screens.auth.components.AuthButton
import com.example.worktimetracker.ui.screens.auth.components.LoginPasswordTextField
import com.example.worktimetracker.ui.screens.auth.components.LoginTextField
import com.example.worktimetracker.ui.theme.poppinsFontFamily
import com.example.worktimetracker.ui.util.BASE_LOG

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onLoginSuccess: (Route) -> Unit,
) {
    val context = LocalContext.current

    val localUserManager = LocalUserManager(context)

    LaunchedEffect(viewModel, context) {
        viewModel.loginUiEvent.collect {
            when (it) {
                is ApiResult.Success -> {
                    localUserManager.saveAccessToken(it.data?._data?.token!!)
                    onLoginSuccess(Route.MainNavigator)
                }

                is ApiResult.Error -> {
                    Log.d("${BASE_LOG}_login_error", it.message)
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBackIos,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = stringResource(id = R.string.login),
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        LoginTextField(
            label = stringResource(id = R.string.username),
            state = viewModel.state,
            hint = stringResource(id = R.string.username_hint),
            onUsernameChange = {
                viewModel.onEvent(LoginUiEvent.UsernameChange(it))
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        LoginPasswordTextField(
            label = stringResource(id = R.string.password),
            state = viewModel.state,
            hint = stringResource(id = R.string.password_hint),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
            ),
            onPasswordChange = {
                viewModel.onEvent(LoginUiEvent.PasswordChange(it))
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        AuthButton(
            text = stringResource(id = R.string.login),
            backgroundColor = colorResource(id = R.color.purple_200),
            onClick = {
                Log.d("app_login_screen", "login button clicked")
                viewModel.onEvent(LoginUiEvent.Login)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(
                thickness = 1.dp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "or",
                color = Color.Black,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        AuthButton(
            text = stringResource(id = R.string.login_google),
            icon = R.drawable.ic_google,
            onClick = {
                viewModel.onEvent(LoginUiEvent.Login)
            }
        )
    }
}

//@Preview(showBackground = true, device = "id:pixel_4")
//@Composable
//private fun LoginPreview() {
//    val navController = rememberNavController()
//    LoginScreen(navController = navController)
//}