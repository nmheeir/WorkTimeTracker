package com.example.worktimetracker.ui.component.Calendar

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.TextStyle.FULL
import java.time.format.TextStyle.SHORT
import java.util.Locale


@Composable
fun CalendarView(
    onDateClick: (LocalDate) -> Unit,
    onMonthChange: (YearMonth) -> Unit
) {
    SelectableCalendar(
        dayContent = { dayState -> MyDay(dayState, onDateClick)},
        monthHeader = { monthState -> MonthHeader(monthState, onMonthChange) },
        daysOfWeekHeader = { daysOfWeek -> MyDayOfWeekHeader(daysOfWeek) },
        showAdjacentMonths = false
    )
}


@Composable
fun MyDayOfWeekHeader(
    daysOfWeek: List<DayOfWeek>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(AppTheme.colors.darkerSurface)
            .padding(vertical = 8.dp)
    ) {
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(SHORT, Locale.getDefault()),
                modifier = modifier
                    .weight(1f)
                    .wrapContentHeight(),
                color = AppTheme.colors.onBackground,
                style = Typography.titleMedium
            )
        }
    }
}


@Composable
fun MyDay(dayState: DayState<DynamicSelectionState>, onDateClick: (LocalDate) -> Unit) {
    val date = dayState.date
    val selectionState = dayState.selectionState

    val isSelected = selectionState.isDateSelected(date) // kiêm tra xem cái ngày được chọn có phải là cái này không
    val backgroundColor = if(isSelected) AppTheme.colors.regularSurface else Color.Transparent
    Card(
        modifier = Modifier
            .height(40.dp)
            .padding(2.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor )
    ) {
        Box (
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onDateClick(date)
                    selectionState.onDateSelected(date)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = dayState.date.dayOfMonth.toString(),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = if(dayState.date.dayOfMonth != LocalDate.now().dayOfMonth ) AppTheme.colors.onBackground else AppTheme.colors.onBackgroundBlue,
                style = Typography.titleMedium
            )
        }
    }
}



@Composable
fun MonthHeader(monthState: MonthState, onMonthChange: (YearMonth) -> Unit) {
    onMonthChange(monthState.currentMonth)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DecrementButton(monthState = monthState)
        Text(
            modifier = Modifier.testTag("MonthLabel"),
            text = monthState.currentMonth.month
                .getDisplayName(FULL, Locale.getDefault())
                .lowercase()
                .replaceFirstChar { it.titlecase() },
            style = Typography.titleLarge,
            color = AppTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = monthState.currentMonth.year.toString(), style = Typography.titleLarge, color = AppTheme.colors.onBackground)
        IncrementButton(monthState = monthState)
    }
}
@Composable
fun DecrementButton(
    monthState: MonthState,
) {
    IconButton(
        modifier = Modifier.testTag("Decrement"),
        enabled = monthState.currentMonth > monthState.minMonth,
        onClick = { monthState.currentMonth = monthState.currentMonth.minusMonths(1) }
    ) {
        Image(
            imageVector = Icons.Default.KeyboardArrowLeft,
            colorFilter = ColorFilter.tint(AppTheme.colors.onBackground),
            contentDescription = "Previous",
        )
    }
}

@Composable
fun IncrementButton(
    monthState: MonthState,
) {
    IconButton(
        modifier = Modifier.testTag("Increment"),
        enabled = monthState.currentMonth < monthState.maxMonth,
        onClick = { monthState.currentMonth = monthState.currentMonth.plusMonths(1) }
    ) {
        Image(
            imageVector = Icons.Default.KeyboardArrowRight,
            colorFilter = ColorFilter.tint(AppTheme.colors.onBackground),
            contentDescription = "Next",
        )
    }
}