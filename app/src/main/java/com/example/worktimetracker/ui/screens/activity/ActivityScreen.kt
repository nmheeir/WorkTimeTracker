package com.example.worktimetracker.ui.screens.activity

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.ui.screens.activity.component.ActivityCalendarDialog
import com.example.worktimetracker.ui.screens.activity.component.DateChose
import com.example.worktimetracker.ui.screens.home.components.ActivitySection.ActivitySectionItem
import com.example.worktimetracker.ui.screens.log.component.LogCalendarDialog
import com.example.worktimetracker.ui.theme.Typography
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    state: ActivityUiState,
    onBack: () -> Unit,
    event: (ActivityUiEvent) -> Unit,
    activityUiEvent: Flow<ApiResult<DataResponse<Check>>>
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    var showCalendarDialogFromDate by remember { mutableStateOf(false) }
    var showCalendarDialogToDate by remember { mutableStateOf(false) }
    val lottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lottie_loading))

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }, topBar = {
        TopAppBar(title = {
            Text(
                text = "Activity", style = Typography.labelLarge
            )
        }, navigationIcon = {
            IconButton(onClick = {
                onBack()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = null
                )
            }
        })
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                DateChose(text = stringResource(R.string.from_day),
                    time = state.fromDate,
                    event = {
                        showCalendarDialogFromDate = !showCalendarDialogFromDate
                    })
                DateChose(text = stringResource(R.string.to_day),
                    time = state.toDate,
                    event = {
                        showCalendarDialogToDate = !showCalendarDialogToDate
                    })
            }
            if(state.isLoaded) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(12.dp)
                ) {
                    items(state.checkList.size) {
                        ActivitySectionItem(item = state.checkList[it])
                    }
                }
            }

            if (showCalendarDialogFromDate) {
                ActivityCalendarDialog(
                    event = { selectedDate ->
                        selectedDate?.let {
                            event(ActivityUiEvent.OnFromDateChange(it))
                        }
                        showCalendarDialogFromDate = false
                    },
                    showDialog = {
                        showCalendarDialogFromDate
                    },
                    selectedDate = state.fromDate
                )
            }

            if (showCalendarDialogToDate) {
                ActivityCalendarDialog(
                    event = { selectedDate ->
                        selectedDate?.let {
                            event(ActivityUiEvent.OnToDateChange(it))
                        }
                        showCalendarDialogToDate = false
                    },
                    showDialog = {
                        showCalendarDialogToDate
                    },
                    selectedDate = state.toDate
                )
            }
        }

        // Loaded
        if (!state.isLoaded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        colorResource(id = R.color.black).copy(alpha = 0.3f)
                    )
                    .clickable(enabled = false, onClick = {})
            ) {
                LottieAnimation(
                    composition = lottieComposition,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityScreenPreview() {
    ActivityScreen(state = ActivityUiState(), onBack = {}, event = {}, activityUiEvent = emptyFlow())
}