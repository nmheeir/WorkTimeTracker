package com.example.worktimetracker.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiEvent
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiState
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.theme.WorkTimeTrackerTheme

@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit,
    state: SharedUiState,
    event: (SharedUiEvent) -> Unit,
    onNavigateTo: (Route) -> Unit
) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            GreetingSection(
                state = state,
                event = event
            )
            OptionSection(
                onNavigateTo = onNavigateTo
            )
            Logout(
                onClick = {
                    onLogoutClick()
                }
            )
        }
}

@Composable
fun EditProfileButton(
    onClick: () -> Unit
) {
    Button(
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.blue),
            contentColor = colorResource(id = R.color.white)
        ),
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Edit profile",
            style = Typography.bodyLarge
        )
    }
}


@Preview
@Composable
fun ProfilePreview() {
    WorkTimeTrackerTheme {
        ProfileScreen(
            onNavigateTo = {},
            onLogoutClick = {},
            state = SharedUiState(),
            event = {}
        )
    }
}