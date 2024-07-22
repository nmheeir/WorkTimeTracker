package com.example.worktimetracker.ui.screens.log.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.data.remote.response.LogStatus
import com.example.worktimetracker.data.remote.response.listLogStatus
import com.example.worktimetracker.ui.screens.log.LogUiState
import com.example.worktimetracker.ui.theme.Typography

@Composable
fun LogDetailSection(
    modifier: Modifier = Modifier,
    state: LogUiState
) {
    var selectedIndex by remember {
        mutableStateOf(listLogStatus[0])
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listLogStatus) { status ->
                LogChipType(
                    text = status,
                    isSelect = status == selectedIndex,
                    onClick = {
                        selectedIndex = status
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        when (selectedIndex) {
            listLogStatus[0] -> {
                val pendingLogs by remember {
                    mutableStateOf(state.listLog.filter {
                        it.status == LogStatus.PENDING.ordinal
                    })
                }
                LogListDetail(list = pendingLogs)
            }

            listLogStatus[1] -> {
                val approvedLogs by remember {
                    mutableStateOf(state.listLog.filter {
                        it.status == LogStatus.APPROVED.ordinal
                    })
                }
                LogListDetail(list = approvedLogs)
            }

            listLogStatus[2] -> {
                val rejectedLogs by remember {
                    mutableStateOf(state.listLog.filter {
                        it.status == LogStatus.REJECTED.ordinal
                    })
                }
                LogListDetail(list = rejectedLogs)
            }
        }
    }
}

@Composable
fun LogChipType(
    modifier: Modifier = Modifier,
    text: String = "Upcoming",
    isSelect: Boolean,
    onClick: () -> Unit
) {

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelect) colorResource(id = R.color.blue) else colorResource(id = R.color.light_gray),
        animationSpec = TweenSpec(200),
        label = "background color"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelect) colorResource(id = R.color.white) else colorResource(id = R.color.black),
        animationSpec = TweenSpec(500),
        label = "text color"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(
                color = backgroundColor
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = text,
            style = Typography.titleMedium,
            fontWeight = FontWeight.Normal,
            color = textColor
        )
    }
}

@Composable
fun LogListDetail(
    list: List<Log>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list.size) {
            LogDetailCard(
                item = list[it]
            )
        }
    }
}

@Composable
fun LogDetailCard(
    modifier: Modifier = Modifier,
    item: Log
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = item.statusColor()).copy(alpha = 0.2f)
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.typeName(),
                    style = Typography.bodyMedium
                )
                Text(
                    text = item.statusName(),
                    style = Typography.bodyMedium,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            color = colorResource(id = item.statusColor()).copy(alpha = 0.5f)
                        )
                        .padding(8.dp)
                )
            }
            Text(
                text = "Check time: " + item.checkTime,
                style = Typography.bodyMedium
            )
            Text(
                text = "Create at: " + item.createAt,
                style = Typography.bodyMedium
            )
        }
    }
}