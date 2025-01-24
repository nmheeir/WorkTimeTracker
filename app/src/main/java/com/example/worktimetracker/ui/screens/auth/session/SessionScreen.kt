package com.example.worktimetracker.ui.screens.auth.session

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.worktimetracker.R
import com.example.worktimetracker.core.presentation.util.ObserveAsEvents
import com.example.worktimetracker.data.local.db.entity.UserSession
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography
import kotlinx.coroutines.flow.Flow


@Composable
fun SessionScreen(
    modifier: Modifier = Modifier,
    channel: Flow<SessionUiEvent>,
    state: List<UserSession>,
    action: (SessionUiAction) -> Unit,
    onLoginSuccess: () -> Unit,
    onNavigateTo: (Route) -> Unit
) {
    val context = LocalContext.current
    ObserveAsEvents(channel) {
        when (it) {
            is SessionUiEvent.Failure -> {
                Toast.makeText(context, it.msg, Toast.LENGTH_LONG).show()
            }

            SessionUiEvent.Success -> {
                onLoginSuccess()
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF2980B9),  // Màu xanh
                            Color(0xFF6DD5FA),   // Màu xanh lá
                            Color(0xFFFFFFFF)
                        )
                    )
                )
        ) {
            val (logo, session, loginAnotherAcc, register) = createRefs()
            Image(
                painter = painterResource(R.drawable.avatar),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .constrainAs(logo) {
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                    }
            )

            LazyColumn(
                modifier = Modifier
                    .constrainAs(session) {
                        top.linkTo(logo.bottom, 128.dp)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                    }
                    .padding(horizontal = 12.dp)
            ) {
                items(state.size) {
                    SessionItem(
                        user = state[it],
                        onClick = {
                            action(SessionUiAction.Login(state[it]))
                        }
                    )
                }
            }

            OutlinedButton(
                border = BorderStroke(1.dp, AppTheme.colors.regularSurface),
                onClick = {
                    onNavigateTo(Route.LoginScreen)
                },
                modifier = Modifier
                    .constrainAs(loginAnotherAcc) {
                        top.linkTo(session.bottom, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                    }

            ) {
                Text(
                    text = "Login another account",
                    style = Typography.bodyLarge
                )
            }
        }
    }
}