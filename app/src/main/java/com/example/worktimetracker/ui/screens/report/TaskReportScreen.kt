package com.example.worktimetracker.ui.screens.report

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.worktimetracker.ui.component.item.ReportCardItem
import com.example.worktimetracker.ui.viewmodels.TaskReportViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskReportScreen(
    navController: NavHostController,
    viewModel: TaskReportViewModel = hiltViewModel()
) {
    val reports by viewModel.reports.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Report") },
                navigationIcon = {
                    IconButton(
                        onClick = navController::navigateUp
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { pv ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = pv
        ) {
            items(
                items = reports
            ) { report ->
                ReportCardItem(
                    report = report,
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setDataAndType(
                            report.reportUrl.toUri(),
                            "application/pdf"
                        )
                        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}