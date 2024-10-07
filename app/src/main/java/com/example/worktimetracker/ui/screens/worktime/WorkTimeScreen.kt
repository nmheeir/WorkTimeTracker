package com.example.worktimetracker.ui.screens.worktime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.profile.component.OptionTopBar
import com.example.worktimetracker.ui.screens.worktime.component.WorkTimeChart
import com.example.worktimetracker.ui.theme.Typography


// TODO: đổi thành summary ?
@Composable
fun WorkTimeScreen(
    state: WorkTimeUiState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            OptionTopBar(
                title = R.string.work_chart,
                onBack = onBack
            )
        }
    ) {
        paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding(), start = 12.dp, end = 12.dp)
        ) {
            WorkTimeChart(
                state = state,
            )
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Total work time", style = Typography.bodySmall)
                Text(text = state.totalWorkTime.toString(), style = Typography.headlineLarge)
            }

            Row (
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                WorkTimeTotalByType(modifier = Modifier.weight(0.7f), Color.Red, 48, "Overtime")
                WorkTimeTotalByType(modifier = Modifier.weight(0.7f), Color.Red, 48, "Overtime")
                WorkTimeTotalByType(modifier = Modifier.weight(0.7f), Color.Red, 48, "Overtime")
            }
        }


    }
}
@Preview(showBackground = true)
@Composable
fun WorkTimeScreenPreview() {
    val sampleState = WorkTimeUiState(
        totalWorkTime = 40 // Giả lập số giờ làm việc
    )
    WorkTimeScreen(
        state = sampleState,
        onBack = {}
    )
}

@Composable
fun WorkTimeTotalByType(modifier: Modifier, color: Color, hour: Long, type: String) {
    Column (
        modifier = modifier
    ) {
        Row {
            Text("Overtime", style = Typography.bodyMedium, color = colorResource(R.color.gray_text))
            Text("x2",
                style = Typography.bodyMedium,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Red)
                    .padding(10.dp, 0.dp),
                color = Color.White
            )
        }
        Row (
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("48 Hour", style = Typography.headlineMedium, color = Color.Red)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
            )
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth(0.1f)
                    .background(Color.Red)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorktimeComponetPreview() {
    WorkTimeTotalByType(modifier = Modifier, Color.Red, 48, "Overtime")
}
