package com.example.worktimetracker.ui.screens.log.component

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.worktimetracker.ui.screens.log.LogUiEvent
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogCalendarDialog(
    modifier: Modifier = Modifier,
    event: (LogUiEvent) -> Unit,
    showDialog: () -> Boolean
) {
    val selectedDate = remember { mutableStateOf<LocalDate?>(LocalDate.now()) }

    CalendarDialog(
        state = rememberUseCaseState(
            visible = showDialog(),
            embedded = true,
            onCloseRequest = {

            },
            onFinishedRequest = {
                event(LogUiEvent.OnDateChange(selectedDate.value.toString()))
                Log.d("Log CLock Dialog", "selectedTime: ${selectedDate.value}")
            }
        ),
        config = CalendarConfig(
            yearSelection = true,
            style = CalendarStyle.WEEK,
        ),
        selection = CalendarSelection.Date(
            selectedDate = selectedDate.value
        ) { newDate ->
            selectedDate.value = newDate
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogClockDialog(
    modifier: Modifier = Modifier,
    event: (LogUiEvent) -> Unit = {},
    showDialog: () -> Boolean
) {
    val selectedTime = remember {
        mutableStateOf(LocalTime.of(8, 20, 0))
    }

    ClockDialog(
        state = rememberUseCaseState(
            visible = showDialog(),
            onDismissRequest = {
                Log.d("Log CLock Dialog", "onDismissRequest")
            },
            onCloseRequest = {
                Log.d("Log CLock Dialog", "onCloseRequest")
            },
            onFinishedRequest = {
                event(LogUiEvent.OnTimeChange(selectedTime.value.toString()))
                Log.d("Log CLock Dialog", "selectedTime: ${selectedTime.value}")
            }
        ),
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            selectedTime.value = LocalTime.of(hours, minutes, 0)
        },
        config = ClockConfig(
            boundary = LocalTime.of(0, 0, 0)..LocalTime.of(23, 59, 0),
            defaultTime = selectedTime.value,
            is24HourFormat = true
        ),
    )
}