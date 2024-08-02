package com.example.worktimetracker.ui.screens.shift

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.DefaultMonthHeader
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.EmptySelectionState
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftScreen(
    onBack: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = "Shift", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding(), start = 12.dp, end = 12.dp)
        ) {
            CalendarView("July")
        }
    }
}


@Composable
fun CalendarView(
    month: String
) {
    StaticCalendar(
        dayContent = { dayState -> MyDay(dayState) },
        monthHeader = { monthState -> MonthShift(monthState) }
    )
}

@Composable
fun MyDay(dayState: DayState<EmptySelectionState>) {
    Log.d("date", dayState.date.dayOfMonth.toString())

    Column(
        modifier = Modifier
            .size(100.dp)
            .border(BorderStroke(1.dp, colorResource(id = R.color.light_gray)))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayState.date.dayOfMonth.toString(),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun MonthShift(monthState: MonthState) {
    DefaultMonthHeader(monthState)
    Log.d("Month", monthState.currentMonth.toString())
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShiftScreen(onBack = {})
}
