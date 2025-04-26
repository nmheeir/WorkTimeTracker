package com.example.worktimetracker.ui.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.worktimetracker.core.presentation.padding
import com.example.worktimetracker.core.presentation.util.Gap
import com.example.worktimetracker.ui.theme.AppTheme

@Composable
fun <E> ChipsRow(
    chips: List<Pair<E, String>>,
    currentValue: E,
    onValueUpdate: (E) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = AppTheme.colors.regularSurface,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        Gap(MaterialTheme.padding.mediumSmall)

        chips.forEach { (value, label) ->
            FilterChip(
                label = { Text(label, color = Color.White) },
                selected = currentValue == value,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = containerColor,
                    selectedLabelColor = Color.Black
                ),
                onClick = { onValueUpdate(value) }
            )

            Gap(MaterialTheme.padding.mediumSmall)
        }
    }
}