package com.example.worktimetracker.ui.screens.salary.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.ui.screens.profile.component.OptionTopBar

@Composable
fun PayCheckDetail(
    paycheck: PayCheck,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            OptionTopBar(
                title = R.string.paycheck_detail,
                onBack = onBack
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    start = 24.dp,
                    end = 24.dp
                )
        ) {
            PayCheckDetailRow(
                title = R.string.total_work_time,
                desc = paycheck.totalWorkTime.toString()
            )
            PayCheckDetailRow(
                title = R.string.normal,
                desc = paycheck.normalWork.toString()
            )
            PayCheckDetailRow(
                title = R.string.overtime,
                desc = paycheck.overtimeWork.toString()
            )
            PayCheckDetailRow(
                title = R.string.night,
                desc = paycheck.nightWork.toString()
            )
            PayCheckDetailRow(
                title = R.string.allowanced,
                desc = paycheck.allowanced.toString()
            )
            PayCheckDetailRow(
                title = R.string.total_income,
                desc = paycheck.totalIncome.toString()
            )
        }
    }
}

@Composable
private fun PayCheckDetailRow(
    @StringRes title: Int,
    desc: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = title))
        Text(text = desc)
    }
}