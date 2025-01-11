import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.component.Calendar.MonthHeader
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.theme.WorkTimeTrackerTheme
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowRight
import compose.icons.fontawesomeicons.solid.ChevronLeft
import compose.icons.fontawesomeicons.solid.ChevronRight
import compose.icons.fontawesomeicons.solid.Clock
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.EmptySelectionState
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle.SHORT
import java.util.Locale

enum class ViewMode {
    DAILY, WEEKLY, MONTHLY
}

@Preview
@Composable
fun PreviewTest() {
    WorkTimeTrackerTheme {
        ScheduleScreen {  }
    }
}
@Composable
fun ScheduleScreen(
    onShiftClick: (Shift) -> Unit
) {
    var viewMode by remember { mutableStateOf(ViewMode.MONTHLY) }
    var currentDate by remember { mutableStateOf(LocalDate.now()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E88E5),
                        Color(0xFF0D47A1)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ScheduleHeader(
                currentDate = currentDate,
                viewMode = viewMode,
                onViewModeChange = { viewMode = it },
                onDateChange = { currentDate = it }
            )
            CalendarView()
            Spacer(modifier = Modifier.height(16.dp))
            MonthlySchedule(currentDate, onShiftClick)
        }
    }
}

@Composable
fun CalendarView(
) {

//    StaticCalendar(
//        dayContent = { dayState -> MyDay(dayState) },
//        monthHeader = { monthState -> MonthHeader(monthState) },
//        daysOfWeekHeader = { daysOfWeek -> MyDayOfWeekHeader(daysOfWeek) },
//        showAdjacentMonths = false
//    )
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
fun MyDay(dayState: DayState<EmptySelectionState>) {
    Column(
        modifier = Modifier
            .height(40.dp)
            .padding(2.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = dayState.date.dayOfMonth.toString(),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = AppTheme.colors.onBackground,
            style = Typography.titleMedium
        )
    }
}
@Composable
fun ScheduleHeader(
    currentDate: LocalDate,
    viewMode: ViewMode,
    onViewModeChange: (ViewMode) -> Unit,
    onDateChange: (LocalDate) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Schedule",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun DateNavigator(
    currentDate: LocalDate,
    viewMode: ViewMode,
    onDateChange: (LocalDate) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                onDateChange(
                    when (viewMode) {
                        ViewMode.DAILY -> currentDate.minusDays(1)
                        ViewMode.WEEKLY -> currentDate.minusWeeks(1)
                        ViewMode.MONTHLY -> currentDate.minusMonths(1)
                    }
                )
            }
        ) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.ChevronLeft,
                contentDescription = "Previous",
                tint = Color.White
            )
        }

        Text(
            text = when (viewMode) {
                ViewMode.DAILY -> currentDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
                ViewMode.WEEKLY -> "Week of ${currentDate.format(DateTimeFormatter.ofPattern("MMMM d"))}"
                ViewMode.MONTHLY -> currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
            },
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )

        IconButton(
            onClick = {
                onDateChange(
                    when (viewMode) {
                        ViewMode.DAILY -> currentDate.plusDays(1)
                        ViewMode.WEEKLY -> currentDate.plusWeeks(1)
                        ViewMode.MONTHLY -> currentDate.plusMonths(1)
                    }
                )
            }
        ) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.ChevronRight,
                contentDescription = "Next",
                tint = Color.White
            )
        }
    }
}

@Composable
fun MonthlySchedule(
    currentDate: LocalDate,
    onShiftClick: (Shift) -> Unit
) {
    val startOfMonth = currentDate.withDayOfMonth(1)
    val daysInMonth = currentDate.lengthOfMonth()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(daysInMonth) { dayOffset ->
            val date = startOfMonth.plusDays(dayOffset.toLong())
            if (getDailyShifts(date).isNotEmpty()) {
                DaySchedule(date, getDailyShifts(date), onShiftClick)
            }
        }
    }
}

@Composable
fun DaySchedule(
    date: LocalDate,
    shifts: List<Shift>,
    onShiftClick: (Shift) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = date.format(DateTimeFormatter.ofPattern("EEEE, MMMM d")),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        shifts.forEach { shift ->
            ShiftCard(shift, onShiftClick)
        }
    }
}

@Composable
fun ShiftCard(
    shift: Shift,
    onClick: (Shift) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(shift) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Shift Time",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "${shift.startTime} - ${shift.endTime}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Duration",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Text(
                        text = shift.duration,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (shift.actualStartTime != null) {
                Divider(color = Color.White.copy(alpha = 0.1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Actual Time",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "${shift.actualStartTime} - ${shift.actualEndTime}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Actual Duration",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = shift.actualDuration ?: "-",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

data class Shift(
    val id: String,
    val date: LocalDate,
    val startTime: String,
    val endTime: String,
    val duration: String,
    val actualStartTime: String? = null,
    val actualEndTime: String? = null,
    val actualDuration: String? = null,
    val notes: String = ""
)

// Sample data function
fun getDailyShifts(date: LocalDate): List<Shift> {
    // This would normally come from your data source
    return listOf(
        Shift(
            id = "1",
            date = date,
            startTime = "09:00",
            endTime = "17:00",
            duration = "8h",
            actualStartTime = "09:05",
            actualEndTime = "17:15",
            actualDuration = "8h 10m"
        )
    )
}