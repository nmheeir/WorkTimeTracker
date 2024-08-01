package com.example.worktimetracker.ui.screens.worktime

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.profile.component.OptionTopBar
import com.example.worktimetracker.ui.screens.worktime.component.WorkTimeChart


// TODO: đổi thành summary ?
@Composable
fun WorkTimeScreen(
    state: WorkTimeUiState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            OptionTopBar(
                title = R.string.work_chart,
                onBack = onBack
            )
        }
    ) {
        WorkTimeChart(
            state = state,
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
        )
    }
}