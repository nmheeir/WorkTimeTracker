package com.example.worktimetracker.ui.test

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.*
import java.time.format.DateTimeFormatter

enum class LogTab {
    PENDING, APPROVED, REJECTED
}

enum class LogType {
    CHECK_IN, CHECK_OUT
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogRegistrationScreen(
    modifier: Modifier = Modifier
) {
    var showRegistrationForm by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(LogTab.PENDING) }

    Box(
        modifier = modifier
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
            // Header with Summary
            LogSummary()

            Spacer(modifier = Modifier.height(16.dp))

            // Add Log Button
            Button(
                onClick = { showRegistrationForm = !showRegistrationForm },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.2f)
                )
            ) {
                Icon(
                    imageVector = if (showRegistrationForm) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = if (showRegistrationForm) "Close form" else "Add log"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (showRegistrationForm) "Cancel Registration" else "Register New Log")
            }

            // Registration Form
            AnimatedVisibility(
                visible = showRegistrationForm,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                LogRegistrationForm(
                    onSubmit = {
                        // Handle submission
                        showRegistrationForm = false
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tabs and Log List
            LogTabs(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    }
}

@Composable
fun LogSummary() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Log Summary",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LogStatistic("Pending", "5", Icons.Default.Pending)
                LogStatistic("Approved", "12", Icons.Default.CheckCircle)
                LogStatistic("Rejected", "3", Icons.Default.Cancel)
            }

            Divider(color = Color.White.copy(alpha = 0.1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total Logs",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = "20",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
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
    onSubmit: () -> Unit
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var selectedType by remember { mutableStateOf(LogType.CHECK_IN) }
    var reason by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
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
                onClick = { /* Show date picker */ },
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
                        text = "Date",
                        color = Color.White
                    )
                    Text(
                        text = selectedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Time Picker
            OutlinedCard(
                onClick = { /* Show time picker */ },
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
                        text = "Time",
                        color = Color.White
                    )
                    Text(
                        text = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Log Type Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LogType.values().forEach { type ->
                    FilterChip(
                        selected = selectedType == type,
                        onClick = { selectedType = type },
                        label = { Text(type.name.replace('_', ' ')) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Reason Input
            OutlinedTextField(
                value = reason,
                onValueChange = { reason = it },
                label = { Text("Reason") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                    unfocusedLabelColor = Color.White.copy(alpha = 0.5f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            // Submit Button
            Button(
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.2f)
                )
            ) {
                Text("Submit Log")
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LogTabs(
    selectedTab: LogTab,
    onTabSelected: (LogTab) -> Unit
) {
    Column {
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = Color.White.copy(alpha = 0.15f),
            contentColor = Color.White
        ) {
            LogTab.values().forEach { tab ->
                Tab(
                    selected = selectedTab == tab,
                    onClick = { onTabSelected(tab) },
                    text = { Text(tab.name.capitalize()) }
                )
            }
        }

        AnimatedContent(
            targetState = selectedTab,
            transitionSpec = {
                fadeIn() with fadeOut()
            }
        ) { tab ->
            LogList(tab)
        }
    }
}

@Composable
fun LogList(tab: LogTab) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(5) { index ->
            LogItem(
                log = Log(
                    id = index,
                    userId = 1,
                    type = if (index % 2 == 0) 0 else 1,
                    status = tab.ordinal,
                    createAt = "2024-01-09T10:00:00",
                    checkTime = "2024-01-09T09:00:00"
                )
            )
        }
    }
}

@Composable
fun LogItem(log: Log) {
    Card(
        modifier = Modifier.fillMaxWidth().height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
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
                    text = if (log.type == 0) "Check In" else "Check Out",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = LocalDateTime.parse(log.checkTime)
                        .format(DateTimeFormatter.ofPattern("MMM dd, HH:mm")),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            Icon(
                imageVector = when (log.status) {
                    0 -> Icons.Default.Pending
                    1 -> Icons.Default.CheckCircle
                    else -> Icons.Default.Cancel
                },
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

data class Log(
    val id: Int,
    val userId: Int,
    val type: Int,
    val status: Int,
    val createAt: String,
    val checkTime: String,
    val user: User? = null
)

data class User(
    val id: Int,
    val name: String
)