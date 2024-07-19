package com.example.worktimetracker.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.screens.home.components.ActivitySection
import com.example.worktimetracker.ui.screens.home.components.HomeGreetingSection
import com.example.worktimetracker.ui.screens.home.components.HomeOptionItem
import com.example.worktimetracker.ui.screens.home.components.NotificationCard
import com.example.worktimetracker.ui.screens.home.components.listHomeOption
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiState

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(
    state: SharedUiState = SharedUiState(),
    onNavigateTo: (Route) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        HomeGreetingSection(
            state = state,
            modifier = Modifier
                .fillMaxHeight(0.12f)
        )
        Box(modifier = Modifier.fillMaxHeight(0.3f)) {
            NotificationCard(
                modifier = Modifier.padding(12.dp)
            )
        }

        LazyHorizontalGrid(
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            rows = GridCells.Fixed(1),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
        ) {
            items(listHomeOption.size) { index ->
                HomeOptionItem(
                    item = listHomeOption[index],
                    onClick = onNavigateTo
                )
            }
        }

        ActivitySection(
            modifier = Modifier.padding(12.dp)
        )

    }
}

