package com.example.worktimetracker.ui.screens.salary

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.ui.screens.profile.component.OptionTopBar
import com.example.worktimetracker.ui.screens.salary.component.PayCheckList

@Composable
fun SalaryScreen(
    state: SalaryState,
    onBack: () -> Unit,
    onShowPayCheckDetail: (PayCheck) -> Unit
) {
    Log.d("SalaryScreen", "state: $state")
    Scaffold(
        topBar = {
            OptionTopBar(
                title = R.string.paycheck,
                onBack = onBack
            )
        },
        containerColor = colorResource(id = R.color.white),
        contentColor = colorResource(id = R.color.white)
    ) {
        PayCheckList(
            modifier = Modifier
                .padding(top = it.calculateTopPadding()),
            state = state,
            onShowPayCheckDetail = { payCheck ->
                onShowPayCheckDetail(payCheck)
            }
        )
    }
}