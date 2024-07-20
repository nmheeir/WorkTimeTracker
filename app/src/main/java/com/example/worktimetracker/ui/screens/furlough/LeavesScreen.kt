package com.example.worktimetracker.ui.screens.furlough

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.worktimetracker.ui.screens.furlough.all_leaves_section.AllLeavesSection
import com.example.worktimetracker.ui.screens.furlough.detail_section.LeavesDetailSection

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LeavesScreen() {
    Column (
    ) {
        AllLeavesSection()
        LeavesDetailSection(
            modifier = Modifier.fillMaxWidth()
//                .padding(paddingValues)
        )
    }
}