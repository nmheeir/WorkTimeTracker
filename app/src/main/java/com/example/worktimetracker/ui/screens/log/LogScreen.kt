package com.example.worktimetracker.ui.screens.log

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.log.component.LogCountSection
import com.example.worktimetracker.ui.screens.log.component.LogCreateDialog
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
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    var mShowLogCreateDialog by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
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
                        mShowLogCreateDialog = !mShowLogCreateDialog
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
            AnimatedVisibility(visible = !mShowLogCreateDialog) {
                LogCountSection(state = state)
            }
            AnimatedVisibility(visible = mShowLogCreateDialog) {
                LogCreateDialog(
                    state = state,
                    event = event,
                    snackBarState = snackBarHostState
                )
            }
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

