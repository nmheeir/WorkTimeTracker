package com.example.worktimetracker.ui.screens.profile.my_profile

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.home.HomeUiState
import com.example.worktimetracker.ui.screens.profile.component.OptionTopBar

@Composable
fun MyProfileScreen(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    onBack: () -> Unit
) {

    var user = state.user

    LaunchedEffect(state) {
        user = state.user
        Log.d("MyProfileScreenLaunchedEffect", user.toString())
    }

    Scaffold(
        topBar = {
            OptionTopBar(
                title = R.string.my_profile,
                onBack = onBack
            )
        },
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.my_profile),
            modifier = Modifier.padding(top = it.calculateTopPadding())
        )
    }
}