package com.example.worktimetracker.ui.component.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.core.ext.format3
import com.example.worktimetracker.core.presentation.padding
import com.example.worktimetracker.data.remote.response.Shift

@Composable
fun ShiftCardItem(
    modifier: Modifier = Modifier,
    shift: Shift
) {
    Card(
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.padding.small)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small)
        ) {
            ShiftTimeSection(
                label1 = "Start",
                label2 = "End",
                value1 = shift.start.format3(),
                value2 = shift.end.format3()
            )
        }
    }
}

@Composable
private fun ShiftTimeSection(
    modifier: Modifier = Modifier,
    label1: String,
    label2: String,
    value1: String,
    value2: String,
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = label1
            )
            Text(
                text = value1
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = label2
            )
            Text(
                text = value2
            )
        }
    }
}