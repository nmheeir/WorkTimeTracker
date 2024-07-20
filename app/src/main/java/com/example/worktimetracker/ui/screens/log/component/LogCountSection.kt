package com.example.worktimetracker.ui.screens.log.component

import android.util.Log
import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.LogStatus
import com.example.worktimetracker.ui.screens.log.LogUiState
import com.example.worktimetracker.ui.theme.Typography

@Composable
fun LogCountSection(
    modifier: Modifier = Modifier,
    state: LogUiState
) {
    val totalLogs = state.listLog
    Log.d("logcount", "state: $state")
    Log.d("logcount", totalLogs.toString())
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            LogTypeCard(
                type = "Total",
                count = {
                    totalLogs.size
                },
                cardBackground = R.color.green
            )

            LogTypeCard(
                type = "Pending",
                count = {
                    totalLogs.count {
                        it.status == LogStatus.PENDING.ordinal
                    }
                },
                cardBackground = R.color.teal
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            LogTypeCard(
                type = "Approved",
                count = {
                    totalLogs.count {
                        it.status == LogStatus.APPROVED.ordinal
                    }
                },
                cardBackground = R.color.blue
            )
            val rejectedLogs by remember {
                mutableStateOf(totalLogs.filter {
                    it.status == LogStatus.REJECTED.ordinal
                })
            }
            LogTypeCard(
                type = "Rejected",
                count = {
                    totalLogs.count {
                        it.status == LogStatus.REJECTED.ordinal
                    }
                },
                cardBackground = R.color.red
            )
        }
    }
}

@Composable
fun LogTypeCard(
    modifier: Modifier = Modifier,
    type: String,
    count: () -> Int,
    @ColorRes cardBackground: Int
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .height(100.dp)
            .aspectRatio(1.5f)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, color = colorResource(id = cardBackground), RoundedCornerShape(16.dp))
            .background(color = colorResource(id = cardBackground).copy(alpha = 0.2f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = type,
            style = Typography.labelLarge,
            maxLines = 2
        )
        Text(
            text = count().toString(),
            style = Typography.labelLarge,
            color = colorResource(id = cardBackground)
        )
    }
}