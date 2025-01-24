package com.example.worktimetracker.ui.screens.salary

import android.util.Log
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.ui.screens.salary.component.PayCheckList
import com.example.worktimetracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalaryScreen(
    state: SalaryState,
    onBack: () -> Unit,
    onShowPayCheckDetail: (PayCheck) -> Unit
) {
    Log.d("SalaryScreen", "state: $state")
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = Color.Transparent,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = "Pay Checks", style = MaterialTheme.typography.titleLarge, color = AppTheme.colors.onBackground) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = null,
                            tint = AppTheme.colors.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
    ) { paddingValues ->
        PayCheckList(
            modifier = Modifier
                .padding(paddingValues),
            state = state,
            onShowPayCheckDetail = { payCheck ->
                onShowPayCheckDetail(payCheck)
            }
        )
    }
}