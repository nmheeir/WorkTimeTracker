package com.example.worktimetracker.ui.screens.salary.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.helper.Helper
import com.example.worktimetracker.ui.screens.salary.SalaryState
import com.example.worktimetracker.ui.theme.Typography

@Composable
fun PayCheckList(
    state: SalaryState,
    modifier: Modifier = Modifier,
    onShowPayCheckDetail: (PayCheck) -> Unit
) {
    var list: List<PayCheck> by remember {
        mutableStateOf(emptyList())
    }
    LaunchedEffect(state) {
        list = state.listPayCheck
    }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
    ) {
        items(list.size) {
            PayCheckItem(
                item = list[it],
                onClick = {
                    Log.d("onClick", list[it].toString())
                    onShowPayCheckDetail(list[it])
                }
            )
        }
    }
}

@Composable
fun PayCheckItem(
    modifier: Modifier = Modifier,
    item: PayCheck,
    onClick: () -> Unit
) {
    val startTime = Helper.convertMillisToDate(item.start)
    val endTime = Helper.convertMillisToDate(item.end)
    Card(
        colors = CardDefaults.cardColors(

        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        val context = LocalContext.current
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text(
                text = "June 2024 Paycheck",
                style = Typography.bodyLarge,
                color = colorResource(id = R.color.blue)
            )
            Text(
                text = "From $startTime to $endTime",
                style = Typography.bodyMedium
            )
            Text(
                text = "View Detail",
                style = Typography.bodySmall,
                color = colorResource(id = R.color.blue),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        onClick()
                    }
            )
        }
    }
}
