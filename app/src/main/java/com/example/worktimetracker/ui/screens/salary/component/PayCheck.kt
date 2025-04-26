package com.example.worktimetracker.ui.screens.salary.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.core.ext.format2
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayCheckItem(
    modifier: Modifier = Modifier,
    item: PayCheck,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.regularSurface,
            contentColor = AppTheme.colors.onRegularSurface
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text(
                text = "From ${item.start.format2()} to ${item.end.format2()}",
                style = Typography.bodyMedium,
                color = AppTheme.colors.onRegularSurface
            )
            var showPayCheckDetail by remember { mutableStateOf(false) }
            TextButton(
                onClick = { showPayCheckDetail = true }
            ) {
                Text(text = "View Detail")
            }
            if (showPayCheckDetail) {
                ModalBottomSheet(
                    dragHandle = null,
                    shape = RoundedCornerShape(0.dp),
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                    onDismissRequest = { showPayCheckDetail = false }
                ) {
                    PaycheckDetail(item = item)
                }
            }
        }
    }
}
