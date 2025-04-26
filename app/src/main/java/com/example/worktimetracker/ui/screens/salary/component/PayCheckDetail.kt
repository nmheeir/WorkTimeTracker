package com.example.worktimetracker.ui.screens.salary.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AddAlarm
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.NightShelter
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.core.presentation.util.hozPadding
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.ui.component.background.LinearBackground
import com.example.worktimetracker.ui.theme.AppTheme
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaycheckDetail(
    item: PayCheck,
) {
    LinearBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .hozPadding()
        ) {
            // Header
            Text(
                text = "Paycheck Detail",
                style = MaterialTheme.typography.headlineMedium,
                color = AppTheme.colors.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Total Income Card
            TotalIncomeCard(item.totalIncome)

            Spacer(modifier = Modifier.height(16.dp))

            // Working Hours Breakdown
            WorkingHoursCard(
                totalHours = item.totalWorkTime,
                normalHours = item.normalWork,
                overtimeHours = item.overtimeWork,
                nightHours = item.nightWork
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Allowance Card
            AllowanceCard(item.allowanced)
        }
    }
}

@Composable
fun TotalIncomeCard(amount: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.regularSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = null,
                    tint = AppTheme.colors.onRegularSurface,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Total Income",
                    style = MaterialTheme.typography.titleMedium,
                    color = AppTheme.colors.blurredText
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = formatCurrency(amount),
                style = MaterialTheme.typography.headlineLarge,
                color = AppTheme.colors.onRegularSurface,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun WorkingHoursCard(
    totalHours: Double,
    normalHours: Double,
    overtimeHours: Double,
    nightHours: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.regularSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = null,
                    tint = AppTheme.colors.onRegularSurface,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Working Hours",
                    style = MaterialTheme.typography.titleMedium,
                    color = AppTheme.colors.onRegularSurface,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            WorkingHoursItem(
                icon = Icons.Default.WatchLater,
                label = "Total Hours",
                hours = totalHours,
                isTotal = true
            )

            Divider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color.White.copy(alpha = 0.1f)
            )

            WorkingHoursItem(
                icon = Icons.Default.Light,
                label = "Normal Hours",
                hours = normalHours
            )

            Spacer(modifier = Modifier.height(12.dp))

            WorkingHoursItem(
                icon = Icons.Default.AddAlarm,
                label = "Overtime Hours",
                hours = overtimeHours
            )

            Spacer(modifier = Modifier.height(12.dp))

            WorkingHoursItem(
                icon = Icons.Default.NightShelter,
                label = "Night Hours",
                hours = nightHours
            )
        }
    }
}

@Composable
fun WorkingHoursItem(
    icon: ImageVector,
    label: String,
    hours: Double,
    isTotal: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AppTheme.colors.blurredText,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = AppTheme.colors.blurredText
            )
        }
        Text(
            text = String.format("%.2f", hours),
            style = if (isTotal) {
                MaterialTheme.typography.titleLarge
            } else {
                MaterialTheme.typography.bodyLarge
            },
            color = AppTheme.colors.onRegularSurface,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun AllowanceCard(amount: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.regularSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Payments,
                    contentDescription = null,
                    tint = AppTheme.colors.onRegularSurface,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Allowance",
                    style = MaterialTheme.typography.titleMedium,
                    color = AppTheme.colors.onRegularSurface,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = formatCurrency(amount),
                style = MaterialTheme.typography.titleMedium,
                color = AppTheme.colors.onRegularSurface
            )
        }
    }
}

private fun formatCurrency(amount: Double): String {
    return NumberFormat.getCurrencyInstance(Locale("vi", "VN")).apply {
        maximumFractionDigits = 0
        currency = Currency.getInstance("VND")
    }.format(amount)
}
