package com.example.worktimetracker.ui.screens.log

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.log.component.LogCountSection
import com.example.worktimetracker.ui.screens.log.component.LogDetailSection
import com.example.worktimetracker.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogScreen(
    state: LogUiState,
    onBack: () -> Unit,
    event: (LogUiEvent) -> Unit
) {
    Log.d("screen_log", state.toString())
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Logs",
                    style = Typography.labelLarge
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    onBack()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    // TODO: create log function
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_square),
                        contentDescription = null
                    )
                }
            }
        )
    }) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    start = 12.dp,
                    end = 12.dp
                )
        ) {
            LogCountSection(state = state)
            LogDetailSection(
                state = state
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun LogScreenPreview() {
    LogScreen(state = LogUiState(), onBack = {}, event = {})
}

