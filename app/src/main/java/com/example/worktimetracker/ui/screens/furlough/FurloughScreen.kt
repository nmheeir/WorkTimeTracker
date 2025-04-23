package com.example.worktimetracker.ui.screens.furlough

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.worktimetracker.ui.screens.furlough.all_leaves_section.AllLeavesSection
import com.example.worktimetracker.ui.screens.furlough.detail_section.LeavesDetailSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FurloughScreen(
    navController: NavHostController,

    ) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "All Leaves")
                },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(Icons.Default.AddBox, null)
                    }
                }
            )
        }
    ) { pv ->
        LazyColumn(
            contentPadding = pv
        ) {
            item(
                key = "all_leaves_section"
            ) {
                AllLeavesSection()
            }

            item(
                key = "leave_detail_section"
            ) {
                LeavesDetailSection(
                    modifier = Modifier.fillMaxWidth()
//                .padding(paddingValues)
                )
            }
        }
    }
}