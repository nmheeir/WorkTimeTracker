package com.example.worktimetracker.ui.component.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.core.presentation.util.hozPadding
import com.example.worktimetracker.data.remote.response.Report
import com.example.worktimetracker.ui.theme.AppTheme

@Composable
fun ReportCardItem(
    modifier: Modifier = Modifier,
    report: Report,
    onClick: () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            contentColor = AppTheme.colors.onRegularSurface,
            containerColor = AppTheme.colors.regularSurface
        ),
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .hozPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = null
            )
            Text(
                text = report.title,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.FileDownload,
                    contentDescription = null
                )
            }
        }
    }
}