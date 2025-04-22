package com.example.worktimetracker.ui.screens.profile.setting

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.profile.component.OptionTopBar

@Composable
fun SettingScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            OptionTopBar(title = R.string.settings, onBack = navController::navigateUp)
        }
    ) {
        Text(
            text = stringResource(id = R.string.settings),
            modifier = Modifier.padding(top = it.calculateTopPadding())
        )
    }
}
