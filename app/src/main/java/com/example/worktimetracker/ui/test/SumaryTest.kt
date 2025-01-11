package com.example.worktimetracker.ui.test

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.ui.screens.worktime.WorkTimeUiState
import com.example.worktimetracker.ui.screens.worktime.component.WorkTimeChart
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Preview
@Composable
fun WorkingSummaryScreen(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth = YearMonth.now()
) {
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
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Text(
                text = "Monthly Summary",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Summary Statistics
            SummaryStatistics()

            Spacer(modifier = Modifier.height(24.dp))

            // Working Hours Chart
            WorkingHoursChart()

            Spacer(modifier = Modifier.height(24.dp))

            // Working Hours Distribution
            WorkingHoursDistribution()
        }
    }
}

@Composable
fun SummaryStatistics() {
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
                text = "Summary",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatisticItem(
                    label = "Working Days",
                    value = "22",
                    modifier = Modifier.weight(1f)
                )
                StatisticItem(
                    label = "Full-time",
                    value = "18",
                    modifier = Modifier.weight(1f)
                )
                StatisticItem(
                    label = "Part-time",
                    value = "4",
                    modifier = Modifier.weight(1f)
                )
            }

            Divider(color = Color.White.copy(alpha = 0.1f))

            StatisticItem(
                modifier = Modifier.fillMaxWidth(),
                label = "Total Hours",
                value = "176",
                suffix = "hours",
                large = true
            )
        }
    }
}

@Composable
fun StatisticItem(
    label: String,
    value: String,
    suffix: String = "",
    large: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.7f)
        )
        Text(
            text = if (suffix.isEmpty()) value else "$value $suffix",
            style = if (large) {
                MaterialTheme.typography.headlineMedium
            } else {
                MaterialTheme.typography.titleLarge
            },
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun WorkingHoursChart() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Daily Working Hours",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))
            WorkTimeChart(
                chartDataState = chartData
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun WorkingHoursDistribution() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Hours Distribution",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Canvas(modifier = Modifier.size(100.dp)) {
                    drawArc(
                        startAngle = -90.0f,
                        sweepAngle = 50f,
                        useCenter = true,
                        color = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ChartLegendItem("Morning (40%)", Color(0xFF64B5F6))
                ChartLegendItem("Afternoon (35%)", Color(0xFF81C784))
                ChartLegendItem("Night (25%)", Color(0xFFE57373))
            }
        }
    }
}

@Composable
fun ChartLegendItem(
    label: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, RoundedCornerShape(2.dp))
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

val chartData = listOf(
    DayWorkTime(
        date = "2025-01-01T08:00:00.000",
        workTime = 4.5,
        type = 0
    ),
    DayWorkTime(
        date = "2025-01-02T09:00:00.000",
        workTime = 3.0,
        type = 1
    ),
    DayWorkTime(
        date = "2025-01-03T10:00:00.000",
        workTime = 6.0,
        type = 2
    ),
    DayWorkTime(
        date = "2025-01-04T11:00:00.000",
        workTime = 7.5,
        type = 0
    ),
    DayWorkTime(
        date = "2025-01-05T12:00:00.000",
        workTime = 5.0,
        type = 1
    ),
    DayWorkTime(
        date = "2025-01-06T13:00:00.000",
        workTime = 2.5,
        type = 2
    )
)
