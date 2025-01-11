package com.example.worktimetracker.ui.screens.worktime

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.AttendanceRecord
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.data.remote.response.TotalWorkTime
import com.example.worktimetracker.helper.ISOFormater
import com.example.worktimetracker.ui.component.NoDataWarning
import com.example.worktimetracker.ui.screens.worktime.component.WorkTimeChart
import com.example.worktimetracker.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Composable
fun WorkTimeScreen(
    channel: Flow<WorkTimeUiEvent>,
    state: WorkTimeUiState,
    onBack: () -> Unit,
    action: (WorkTimeUiAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Text(
            text = stringResource(R.string.monthly_summary),
            style = MaterialTheme.typography.headlineMedium,
            color = AppTheme.colors.onBackground,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))
        MyMonthHeader(
            month = state.month,
            onLeftArrowClick = {action(WorkTimeUiAction.OnMonthChange(1))},
            onRightArrowClick = {action(WorkTimeUiAction.OnMonthChange(2))}
        )


        // Summary Statistics
        SummaryStatistics(state.attendanceRecord, state.totalWorkTime)

        Spacer(modifier = Modifier.height(24.dp))

        // Working Hours Chart
        WorkingHoursChart(state.chartData, state.isChartDataLoading)

        Spacer(modifier = Modifier.height(24.dp))

        // Working Hours Distribution
        WorkingHoursDistribution(state.totalWorkTime)
    }
}


@SuppressLint("DefaultLocale")
@Composable
fun SummaryStatistics(
    attendanceRecord: AttendanceRecord,
    totalWorkTime: TotalWorkTime
) {
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
                text = stringResource(R.string.attendance_record),
                style = MaterialTheme.typography.titleLarge,
                color = AppTheme.colors.onRegularSurface,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatisticItem(
                    label = "Full time",
                    value = attendanceRecord.fullAttendance.toString(),
                    modifier = Modifier.weight(1f)
                )
                StatisticItem(
                    label = "Part-time",
                    value = attendanceRecord.partialAttendance.toString(),
                    modifier = Modifier.weight(1f)
                )
                StatisticItem(
                    label = "Absence",
                    value = attendanceRecord.absenceAttendance.toString(),
                    modifier = Modifier.weight(1f)
                )
            }
            Divider(color = Color.White.copy(alpha = 0.1f))
            Text(
                text = stringResource(R.string.worktime_record),
                style = MaterialTheme.typography.titleLarge,
                color = AppTheme.colors.onRegularSurface,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatisticItem(
                    label = "Normal",
                    value = String.format("%.1f", totalWorkTime.normalHours),
                    modifier = Modifier.weight(1f)
                )
                StatisticItem(
                    label = "Nighttime",
                    value = String.format("%.1f", totalWorkTime.nightHours),
                    modifier = Modifier.weight(1f)
                )
                StatisticItem(
                    label = "Overtime",
                    value = String.format("%.1f", totalWorkTime.overtimeHours),
                    modifier = Modifier.weight(1f)
                )
            }

            Divider(color = Color.White.copy(alpha = 0.1f))


            StatisticItem(
                modifier = Modifier.fillMaxWidth(),
                label = "Total Hours",
                value = String.format("%.1f", totalWorkTime.totalHours),
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
            color = AppTheme.colors.blurredText
        )
        Text(
            text = if (suffix.isEmpty()) value else "$value $suffix",
            style = if (large) {
                MaterialTheme.typography.headlineMedium
            } else {
                MaterialTheme.typography.titleLarge
            },
            color = AppTheme.colors.onRegularSurface,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun WorkingHoursChart(
    charData: List<DayWorkTime>,
    isLoading: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.regularSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.work_chart),
                style = MaterialTheme.typography.titleLarge,
                color = AppTheme.colors.onRegularSurface,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))
            if (charData.isNotEmpty() && !isLoading) {
                WorkTimeChart(
                    chartDataState = charData
                )
            }
            else {
                NoDataWarning()
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun WorkingHoursDistribution(
    totalWorkTime: TotalWorkTime
) {
    val normalSwipeAngle = (totalWorkTime.normalHours.toFloat() / totalWorkTime.totalHours.toFloat()) * 360f
    val nightSwipeAngle = (totalWorkTime.nightHours.toFloat() / totalWorkTime.totalHours.toFloat()) * 360f
    val overTimeSwipeAngle = (totalWorkTime.overtimeHours.toFloat() / totalWorkTime.totalHours.toFloat()) * 360f
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
                Canvas(modifier = Modifier.size(200.dp)) {
                    drawArc(
                        startAngle = -90.0f,
                        sweepAngle = normalSwipeAngle,
                        useCenter = true,
                        color = Color(0xFF64B5F6)
                    )
                    drawArc(
                        startAngle = -90.0f + normalSwipeAngle ,
                        sweepAngle = nightSwipeAngle,
                        useCenter = true,
                        color =  Color(0xFF81C784)
                    )
                    drawArc(
                        startAngle = -90.0f + normalSwipeAngle + nightSwipeAngle ,
                        sweepAngle = overTimeSwipeAngle,
                        useCenter = true,
                        color = Color(0xFFE57373)
                    )

                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ChartLegendItem("Normal (40%)", Color(0xFF64B5F6))
                ChartLegendItem("Night (35%)", Color(0xFF81C784))
                ChartLegendItem("Overtime (25%)", Color(0xFFE57373))
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

@Composable
fun MyMonthHeader(
    month: LocalDate,
    onLeftArrowClick: () -> Unit,
    onRightArrowClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        // Mũi tên trái
        IconButton(
            onClick = onLeftArrowClick,
            modifier = Modifier
                .size(48.dp) // Kích thước của icon
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft, // Mũi tên trái
                contentDescription = "Left Arrow",
                tint = Color.White // Đặt màu trắng cho icon
            )
        }

        // Tiêu đề
        Text(
            text = ISOFormater.LocalDateFormatDate1(month),
            style = MaterialTheme.typography.headlineMedium,
            color = AppTheme.colors.onRegularSurface
        )

        // Mũi tên phải
        IconButton(
            onClick = onRightArrowClick,
            modifier = Modifier
                .size(48.dp) // Kích thước của icon
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight, // Mũi tên phải
                contentDescription = "Right Arrow",
                tint = Color.White // Đặt màu trắng cho icon
            )
        }
    }
}




