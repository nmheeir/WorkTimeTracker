package com.example.worktimetracker.ui.screens.log.component

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.LogType
import com.example.worktimetracker.ui.component.dateTimePicker.CalendarDialog
import com.example.worktimetracker.ui.component.dateTimePicker.DateChose
import com.example.worktimetracker.ui.component.dateTimePicker.TimeChose
import com.example.worktimetracker.ui.component.dateTimePicker.TimePickerDialog
import com.example.worktimetracker.ui.screens.log.LogUiEvent
import com.example.worktimetracker.ui.screens.log.LogUiState
import com.example.worktimetracker.ui.theme.Typography


@Composable
fun LogCreateDialog(
    state: LogUiState,
    event: (LogUiEvent) -> Unit,
    snackBarState: SnackbarHostState,
) {
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
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DateChose(
                text = "Chose date",
                time = state.date,
                event = {
                    showCalendarDialog = !showCalendarDialog
                })
            TimeChose(
                text = "Chose time",
                time = state.time,
                event = {
                    showClockDialog = !showClockDialog
                }
            )
        }
        Button(
            onClick = {
                    event(LogUiEvent.CreateLog)
                    Log.d("log_create", "LogCreateDialog: $state")
            },
            modifier = Modifier.
                fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.blue) // Đặt màu nền từ resource
            )
        ) {
            Text(text = "Create Log", color = colorResource(R.color.white))
        }
    }
    if (showClockDialog) {
        TimePickerDialog(
            event = {
                selectedTime ->
                    selectedTime?.let {
                        event(LogUiEvent.OnTimeChange(it))
                    }
                showClockDialog = false
            },
            showDialog = {
                showClockDialog
            },
            selectedTime = state.time
        )
    }
    if (showCalendarDialog) {
        CalendarDialog(
            event = { selectedDate ->
                selectedDate?.let {
                    event(LogUiEvent.OnDateChange(it))
                }
                showCalendarDialog = false
            },
            showDialog = {
                showCalendarDialog
            },
            selectedDate = state.date
        )
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