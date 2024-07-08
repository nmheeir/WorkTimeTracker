package com.example.worktimetracker.ui.screens.auth.login

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.navigation.navigateSingleTopTo
import com.example.worktimetracker.ui.screens.auth.components.AuthButton
import com.example.worktimetracker.ui.screens.auth.components.AuthPasswordTextField
import com.example.worktimetracker.ui.screens.auth.components.AuthTextField

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val viewModel = hiltViewModel<LoginViewModel>()
    val loginSuccess by viewModel.loginSuccess.observeAsState()

    loginSuccess?.let {
        if (it) {
            navController.navigateSingleTopTo(Route.HomeScreen.route)
        }
        else {
            Toast.makeText(LocalContext.current, "Login failed", Toast.LENGTH_SHORT).show()
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
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        AuthTextField(
            label = stringResource(id = R.string.username),
            text = username,
            hint = stringResource(id = R.string.username_hint),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            onChangeValue = {
                username = it
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        AuthPasswordTextField(
            label = stringResource(id = R.string.password),
            text = password,
            hint = stringResource(id = R.string.password_hint),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
            ),
            onChangeValue = {
                password = it
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        AuthButton(
            text = stringResource(id = R.string.login),
            backgroundColor = colorResource(id = R.color.purple_200),
            onClick = {
                viewModel.login(username, password)
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

            }
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_4")
@Composable
private fun LoginPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}