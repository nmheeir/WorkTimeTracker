package com.example.worktimetracker.ui.screens.log.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.Log
import com.example.worktimetracker.data.remote.response.LogStatus
import com.example.worktimetracker.data.remote.response.exampleLogs
import com.example.worktimetracker.ui.screens.log.LogUiState
import com.example.worktimetracker.ui.theme.Typography
import kotlinx.coroutines.launch


// TODO: clean code
@Composable
fun LogDetailSection(
    modifier: Modifier = Modifier,
    state: LogUiState
) {
    LogDetailPager(
        state = state
    )
}

@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LogDetailPager(
    modifier: Modifier = Modifier,
    state: LogUiState = LogUiState(listLog = exampleLogs)
) {
    var pendingLogs: List<Log> by remember {
        mutableStateOf(emptyList())
    }
    var approvedLogs: List<Log> by remember {
        mutableStateOf(emptyList())
    }
    var rejectedLogs: List<Log> by remember {
        mutableStateOf(emptyList())
    }

    val pageState = rememberPagerState(
        initialPage = 0,
        pageCount = { 3 }
    )
    val coroutineScope = rememberCoroutineScope()
    val selectedIndex = remember {
        derivedStateOf { pageState.currentPage }
    }

    LaunchedEffect(state) {
        pendingLogs = state.listLog.filter {
            it.status == LogStatus.PENDING.ordinal
        }
        approvedLogs = state.listLog.filter {
            it.status == LogStatus.APPROVED.ordinal
        }
        rejectedLogs = state.listLog.filter {
            it.status == LogStatus.REJECTED.ordinal
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TabRow(
            divider = {},
            indicator = {},
            selectedTabIndex = selectedIndex.value
        ) {
            LogStatus.entries.forEachIndexed { index, currentTab ->
                val isSelected = selectedIndex.value == index
                val bgColor = if (isSelected) colorResource(id = R.color.blue) else colorResource(id = R.color.white)
                val textColor = if (isSelected) colorResource(id = R.color.white) else colorResource(id = R.color.blue)
                Tab(
                    selected = isSelected,
                    onClick = {
                        coroutineScope.launch {
                            pageState.animateScrollToPage(currentTab.ordinal)
                        }
                    }
                ) {
                    Text(
                        text = currentTab.displayStatus(),
                        style = Typography.labelLarge,
                        textAlign = TextAlign.Center,
                        color = textColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(bgColor)
                            .padding(8.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalPager(
            verticalAlignment = Alignment.Top,
            state = pageState,
            modifier = Modifier
                .fillMaxWidth()
        ) { index ->
            when (index) {
                0 -> {
                    LogListDetail(list = pendingLogs)
                }

                1 -> {
                    LogListDetail(list = approvedLogs)
                }

                2 -> {
                    LogListDetail(list = rejectedLogs)
                }

                else -> {
                    //Nothing
                }
            }
        }
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