package com.example.worktimetracker.ui.screens.log.component

import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.LogType
import com.example.worktimetracker.ui.screens.log.LogUiEvent
import com.example.worktimetracker.ui.screens.log.LogUiState
import com.example.worktimetracker.ui.theme.Typography
import kotlinx.coroutines.launch


@Composable
fun LogCreateDialog(
    modifier: Modifier = Modifier,
    state: LogUiState,
    snackBarState : SnackbarHostState,
    event: (LogUiEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var isSelected by remember { mutableStateOf(false) }
    var showClockDialog by remember { mutableStateOf(false) }
    var showCalendarDialog by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()
        ) {
            LogCreateTypeButton(isSelected = !isSelected,
                type = LogType.CHECK_IN,
                icon = R.drawable.ic_login,
                onClick = {
                    isSelected = !isSelected
                    event(LogUiEvent.OnLogTypeChange(LogType.CHECK_IN))
                })
            LogCreateTypeButton(isSelected = isSelected,
                type = LogType.CHECK_OUT,
                icon = R.drawable.ic_logout,
                onClick = {
                    isSelected = !isSelected
                    event(LogUiEvent.OnLogTypeChange(LogType.CHECK_OUT))
                })
        }
        Button(onClick = {
            showClockDialog = !showClockDialog
        }) {
            Text(text = "Choose Time" + state.time)
        }
        Button(onClick = {
            showCalendarDialog = !showCalendarDialog
        }) {
            Text(text = "Choose Date " + state.date)
        }
        Button(
            onClick = {
                if (handleCreateLogError(state)) {
                    coroutineScope.launch {
                        snackBarState.showSnackbar(
                            message = "Please fill all fields",
                            duration = SnackbarDuration.Short,
                            actionLabel = "Dismiss"
                        )
                    }
                } else {
                    event(LogUiEvent.CreateLog)
                    Log.d("log_create", "LogCreateDialog: $state")
                }
            }
        ) {
            Text(text = "Create Log")
        }
    }
    if (showClockDialog) {
        LogClockDialog(event = event, showDialog = {
            showClockDialog
        })
    }
    if (showCalendarDialog) {
        LogCalendarDialog(event = event, showDialog = {
            showCalendarDialog
        })
    }
}

@Composable
fun LogCreateTypeButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    @DrawableRes icon: Int,
    type: LogType,
    onClick: () -> Unit
) {
    val bgColor =
        if (isSelected) colorResource(id = R.color.blue) else colorResource(id = R.color.white)
    val contentColor =
        if (isSelected) colorResource(id = R.color.white) else colorResource(id = R.color.blue)
    Card(colors = CardDefaults.cardColors(
        containerColor = bgColor, contentColor = contentColor
    ), shape = RoundedCornerShape(8.dp), modifier = modifier.clickable {
        onClick()
    }) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = icon), contentDescription = null, tint = contentColor
            )
            Text(
                text = type.displayType(), style = Typography.bodySmall
            )
        }
    }
}

fun handleCreateLogError(state: LogUiState): Boolean {
    return state.date.isEmpty() || state.time.isEmpty()
}