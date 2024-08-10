package com.example.worktimetracker.ui.screens.shift

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.R
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.helper.Helper
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.DefaultMonthHeader
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.EmptySelectionState
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftScreen(
    onBack: () -> Unit,
    viewModel: ShiftViewModel
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    val context = LocalContext.current


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
            CalendarView("July", viewModel)
        }
    }
}


@Composable
fun CalendarView(
    month : String,
    viewModel: ShiftViewModel,
) {
    StaticCalendar(
        dayContent = { dayState -> MyDay(dayState, viewModel) },
        monthHeader = { monthState -> MonthShift(monthState, viewModel) },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun MyDay(dayState: DayState<EmptySelectionState>, viewModel: ShiftViewModel) {
    Column(
        modifier = Modifier
            .size(100.dp)
            .border(BorderStroke(1.dp, colorResource(id = R.color.light_gray)))
            .fillMaxSize()
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayState.date.dayOfMonth.toString(),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        ShiftShow(viewModel, dayState)
    }
}

@Composable
fun ShiftShow(viewModel: ShiftViewModel, dayState: DayState<EmptySelectionState>) {
    if (!viewModel.state.isLoading) {
        val shifts = viewModel.state.shiftMap?.get(dayState.date.dayOfMonth) ?: emptyList()
        Column {
            for (shift in shifts) {
                if(shift.month == dayState.date.monthValue) {
                    Text(
                        text = Helper.convertMillisToTimeStamp(shift.start) + " -",
                        style = TextStyle(
                            color = Color.Green, // Màu xanh lá cây
                            fontSize = 12.sp,     // Kích thước chữ nhỏ
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        text = Helper.convertMillisToTimeStamp(shift.end),
                        style = TextStyle(
                            color = Color.Green, // Màu xanh lá cây
                            fontSize = 12.sp,     // Kích thước chữ nhỏ
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
    }

}

@Composable
fun MonthShift(monthState: MonthState, viewModel: ShiftViewModel) {
    DefaultMonthHeader(monthState)

    LaunchedEffect(monthState.currentMonth) {
        viewModel.onEvent(ShiftUiEvent.GetMyShiftsInMonth(monthState.currentMonth.monthValue, monthState.currentMonth.year))
    }

// Theo dõi sự thay đổi của isLoading

    LaunchedEffect(viewModel.state.isLoading) {
        if (!viewModel.state.isLoading ) {
            Log.d("ShiftScreen", "Shifts: ${viewModel.state.shiftList}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    ShiftScreen(onBack = {})
}
