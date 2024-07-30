package com.example.worktimetracker.ui.screens.salary

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.screens.profile.component.OptionTopBar
import com.example.worktimetracker.ui.screens.salary.component.PayCheckList

@Composable
fun SalaryScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
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
                .padding(top = it.calculateTopPadding())
        )
    }
}