package com.example.worktimetracker.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.navigation.navigateSingleTopTo
import com.example.worktimetracker.ui.screens.auth.components.AuthButton

@Composable
fun AuthScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.auth_welcome),
                fontSize = 32.sp,
                style = MaterialTheme.typography.titleMedium,
                letterSpacing = 1.sp,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.auth_des),
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                letterSpacing = 1.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            AuthButton(
                text = stringResource(id = R.string.login).uppercase(),
                backgroundColor = colorResource(id = R.color.purple_200),
                onClick = {
                    navController.navigateSingleTopTo(Route.LoginScreen.route)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            AuthButton(
                text = stringResource(id = R.string.create_account).uppercase(),
                onClick = {
                    navController.navigate(Route.SignUpScreen.route)
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AuthScreenPreview() {
    val navController = rememberNavController()
    AuthScreen(navController = navController)
}