package com.example.worktimetracker.ui.screens.log

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.core.presentation.util.ObserveAsEvents
import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.data.remote.response.LogStatus
import com.example.worktimetracker.data.remote.response.LogType
import com.example.worktimetracker.helper.ISOFormater
import com.example.worktimetracker.ui.component.dateTimePicker.CalendarDialog
import com.example.worktimetracker.ui.component.dateTimePicker.TimePickerDialog
import com.example.worktimetracker.ui.component.dialog.SuccessDialog
import com.example.worktimetracker.ui.screens.check.checkPage.CheckUiEvent
import com.example.worktimetracker.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogScreen(
    state: LogUiState,
    onBack: () -> Unit,
    action: (LogUiAction) -> Unit,
    channel: Flow<LogUiEvent>
) {
    val context = LocalContext.current

    // form state
    var showRegistrationForm by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(LogStatus.PENDING) }

    // Dialog
    var dialogContent by remember  { mutableStateOf("") }
    var isSuccess by remember  { mutableStateOf(true) }
    var isVisible by remember  { mutableStateOf(false) }
    ObserveAsEvents(channel) {
        when(it) {
            LogUiEvent.CreateLogSuccess -> {
                dialogContent = context.getString(R.string.create_log_success)
                isVisible = true
            }

            is LogUiEvent.Failure -> {
                dialogContent = it.message
                isSuccess = false
                isVisible = true
            }

            LogUiEvent.Success -> {

            }
        }
    }

    if(isVisible) {
        SuccessDialog(isSuccess, dialogContent, { isVisible = false })
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with Summary
        item {
            LogSummary(state.listLog)

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Add Log Button
        item {
            Button(
                onClick = { showRegistrationForm = !showRegistrationForm },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.regularSurface
                )
            ) {
                Icon(
                    imageVector = if (showRegistrationForm) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = if (showRegistrationForm) "Close form" else "Add log"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (showRegistrationForm) stringResource(R.string.cancel_register_log) else stringResource(R.string.register_log))
            }
        }

        // Registration Form
        item {
            AnimatedVisibility(
                visible = showRegistrationForm,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                LogRegistrationForm(
                    action = action,
                    logType = state.type,
                    time = state.time,
                    date = state.date,
                    reason = state.reason
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Tabs and Log List
        item {
            LogTabs(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                logList = state.listLog,
            )
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Lớp phủ mờ
                .clickable(enabled = false, onClick = {}), // Vô hiệu hóa click xuyên qua
            contentAlignment = Alignment.Center // Căn giữa vòng xoay
        ) {
            CircularProgressIndicator(color = AppTheme.colors.blurredText)
        }
    }
}

@Composable
fun LogSummary(
    logList: List<Log>
) {
    val pendingLogs = logList.filter { it.status == 0 }.size
    val approvedLog = logList.filter { it.status == 1 }.size
    val rejectedLog = logList.filter { it.status == 2 }.size

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.regularSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.log_sumary_heaer),
                style = MaterialTheme.typography.titleLarge,
                color = AppTheme.colors.onRegularSurface,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LogStatistic(LogStatus.PENDING.displayStatus(), pendingLogs.toString(), Icons.Default.Pending)
                LogStatistic(LogStatus.APPROVED.displayStatus(), approvedLog.toString(), Icons.Default.CheckCircle)
                LogStatistic(LogStatus.REJECTED.displayStatus(), rejectedLog.toString(), Icons.Default.Cancel)
            }

            Divider(color = Color.White.copy(alpha = 0.1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.total_log),
                    style = MaterialTheme.typography.titleMedium,
                    color = AppTheme.colors.onRegularSurface
                )
                Text(
                    text = logList.size.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = AppTheme.colors.onRegularSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun LogStatistic(
    label: String,
    value: String,
    icon: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogRegistrationForm(
    action: (LogUiAction) -> Unit,
     logType: LogType = LogType.CHECK_IN,
     time: LocalTime = LocalTime.now(),
     date: LocalDate = LocalDate.now(),
     reason : String = "",
) {
    var showClockDialog by remember { mutableStateOf(false) }
    var showCalendarDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.regularSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Date Picker
            OutlinedCard(
                onClick = { showCalendarDialog = true },
                colors = CardDefaults.outlinedCardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.date),
                        color = AppTheme.colors.onRegularSurface
                    )
                    Text(
                        text = date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                        color = AppTheme.colors.onRegularSurface,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Time Picker
            OutlinedCard(
                onClick = { showClockDialog = true },
                colors = CardDefaults.outlinedCardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.time),
                        color = AppTheme.colors.onRegularSurface
                    )
                    Text(
                        text = time.format(DateTimeFormatter.ofPattern("HH:mm")),
                        color = AppTheme.colors.onRegularSurface,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Log Type Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LogType.entries.forEach { type ->
                    FilterChip(
                        selected = logType == type,
                        onClick = {  action(LogUiAction.OnLogTypeChange(type)) },
                        label = { Text(type.name.replace('_', ' ')) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Reason Input
            OutlinedTextField(
                value = reason,
                onValueChange = { action(LogUiAction.OnReasonChange(it)) },
                label = { Text(stringResource(R.string.reason)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = AppTheme.colors.unfocusedBorderTextField,
                    unfocusedLabelColor = AppTheme.colors.unfocusedBorderTextField,
                    focusedTextColor = AppTheme.colors.onRegularSurface,
                    unfocusedTextColor = AppTheme.colors.onRegularSurface
                )
            )

            // Submit Button
            Button(
                onClick = { action(LogUiAction.CreateLog) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.actionSurface,
                    contentColor = AppTheme.colors.onActionSurface
                )
            ) {
                Text("Submit Log")
            }
        }
    }

    if (showClockDialog) {
        TimePickerDialog(
            event = {
                    selectedTime ->
                selectedTime?.let {
                    action(LogUiAction.OnTimeChange(it))
                }
                showClockDialog = false
            },
            showDialog = {
                showClockDialog
            },
            selectedTime = time
        )
    }
    if (showCalendarDialog) {
        CalendarDialog(
            event = { selectedDate ->
                selectedDate?.let {
                    action(LogUiAction.OnDateChange(it))
                }
                showCalendarDialog = false
            },
            showDialog = {
                showCalendarDialog
            },
            selectedDate = date
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LogTabs(
    selectedTab: LogStatus,
    onTabSelected: (LogStatus) -> Unit,
    logList: List<Log>,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = AppTheme.colors.regularSurface,
            contentColor = AppTheme.colors.onRegularSurface
        ) {
            LogStatus.entries.forEach { type ->
                Tab(
                    selected = selectedTab == type,
                    onClick = { onTabSelected(type) },
                    text = { Text(type.name.capitalize(Locale.ROOT)) }
                )
            }
        }

        AnimatedContent(
            targetState = selectedTab,
            transitionSpec = {
                fadeIn() with fadeOut()
            }, label = ""
        ) { type ->
            LogList(type, logList)
        }
    }
}

@Composable
fun LogList(type: LogStatus, logList: List<Log>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().heightIn(max = 80.dp * 7),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(logList.filter { it.status == type.ordinal }) { log ->
            LogItem(
                log = log
            )
        }
    }
}

@Composable
fun LogItem(log: Log) {
    Card(
        modifier = Modifier.fillMaxWidth().height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.regularSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (log.type == 0) stringResource(R.string.check_in) else stringResource(R.string.check_out),
                    style = MaterialTheme.typography.titleMedium,
                    color = AppTheme.colors.onRegularSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = ISOFormater.formatDateTimeToDate1(log.checkTime),
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.colors.blurredText
                )
            }

            Icon(
                imageVector = when (log.status) {
                    0 -> Icons.Default.Pending
                    1 -> Icons.Default.CheckCircle
                    else -> Icons.Default.Cancel
                },
                contentDescription = null,
                tint = AppTheme.colors.onRegularSurface
            )
        }
    }
}



