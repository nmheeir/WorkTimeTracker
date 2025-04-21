package com.example.worktimetracker.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.navigation.Screens
import com.example.worktimetracker.ui.screens.home.components.HomeGreetingSection
import com.example.worktimetracker.ui.screens.home.components.HomeOptionItem
import com.example.worktimetracker.ui.screens.home.components.HomeOptionItemData
import com.example.worktimetracker.ui.screens.home.components.NotificationPager
import com.example.worktimetracker.ui.screens.home.components.listHomeNotification
import com.example.worktimetracker.ui.screens.home.components.listHomeOption
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiState
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.viewmodels.HomeViewModel

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
// TODO: Sửa lại UI và thêm viewModel cho HomeScreen
fun HomeScreen(
    state: SharedUiState = SharedUiState(),
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topSection, greetingSection, notifySection, optionSection) = createRefs()
        HomeGreetingSection(
            state = state,
            modifier = Modifier.constrainAs(greetingSection) {
                top.linkTo(topSection.top)
                start.linkTo(topSection.start)
                end.linkTo(topSection.end)
            },
            onAvatarClick = {
                navController.navigate(Screens.ProfileScreen.route)
            }
        )

        NotificationPager(
            listNotify = listHomeNotification,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .constrainAs(notifySection) {
                    top.linkTo(greetingSection.bottom, margin = 8.dp)
                })

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(optionSection) {
                    top.linkTo(notifySection.bottom)
                }) {
            items(5) { index ->
                HomeOptionItem(item = listHomeOption[index], onClick = {
                    navController.navigate(it.route)
                })
            }
            item {
                HomeOptionItem(
                    item = HomeOptionItemData(
                        title = "More", icon = R.drawable.ic_more, color = R.color.black
                    ), onShowDialog = {
                        showBottomSheet = true
                    })
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = AppTheme.colors.backgroundStart
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(16.dp)
            ) {
                items(listHomeOption.size) { index ->
                    HomeOptionItem(
                        item = listHomeOption[index],
                        onClick = {
                            navController.navigate(it.route)
                        }
                    )
                }
            }
        }
    }

}