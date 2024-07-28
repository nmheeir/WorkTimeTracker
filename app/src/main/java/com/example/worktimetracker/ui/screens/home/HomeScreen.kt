package com.example.worktimetracker.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.screens.home.components.ActivitySection
import com.example.worktimetracker.ui.screens.home.components.HomeGreetingSection
import com.example.worktimetracker.ui.screens.home.components.HomeOptionItem
import com.example.worktimetracker.ui.screens.home.components.HomeOptionItemData
import com.example.worktimetracker.ui.screens.home.components.NotificationCard
import com.example.worktimetracker.ui.screens.home.components.listHomeOption
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiState

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(
    state: SharedUiState = SharedUiState(), onNavigateTo: (Route) -> Unit = {}
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topSection, greetingSection, notifySection, optionSection, activitySection) = createRefs()
        Box(modifier = Modifier
            .fillMaxHeight(0.25f)
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp),
                color = colorResource(id = R.color.blue)
            )
            .constrainAs(topSection) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        HomeGreetingSection(modifier = Modifier.constrainAs(greetingSection) {
            top.linkTo(topSection.top)
            start.linkTo(topSection.start)
            end.linkTo(topSection.end)
        })
        NotificationCard(modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .constrainAs(notifySection) {
                top.linkTo(greetingSection.bottom, margin = 8.dp)
            })
        LazyVerticalGrid(columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(optionSection) {
                    top.linkTo(notifySection.bottom)
                }) {
            items(5) { index ->
                HomeOptionItem(item = listHomeOption[index], onClick = {
                    onNavigateTo(it)
                })
            }
            item {
                HomeOptionItem(item = HomeOptionItemData(
                    title = "More", icon = R.drawable.ic_more, color = R.color.black
                ), onShowDialog = {
                    showBottomSheet = true
                })
            }
        }
        ActivitySection(modifier = Modifier
            .padding(12.dp)
            .constrainAs(activitySection) {
                top.linkTo(optionSection.bottom, margin = 16.dp)
            })
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(16.dp)
            ) {
                items(listHomeOption.size) { index ->
                    HomeOptionItem(item = listHomeOption[index], onClick = {
                        onNavigateTo(it)
                    })
                }
            }
        }
    }
}

