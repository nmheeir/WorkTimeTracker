package com.example.worktimetracker.ui.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.worktimetracker.R
import com.example.worktimetracker.ui.navigation.HomeNavGraph
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.navigation.lNavItem
import com.example.worktimetracker.ui.screens.home.activity_section.ActivitySection
import com.example.worktimetracker.ui.screens.home.attendance_section.AttendanceSection
import com.example.worktimetracker.ui.screens.home.header_section.HeaderSection

@Composable
fun AppScaffold(
    navController: NavHostController = rememberNavController(),
    logout: () -> Unit,
    viewModel: HomeViewModel,
    onNavigateTo: (Route) -> Unit
) {
    LaunchedEffect(viewModel) {
        Log.d("viewmodel_app_scaffold", viewModel.state.toString())
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Box(
            modifier = Modifier.padding(bottom = it.calculateBottomPadding())
        ) {
            Log.d("viewmodel_home", "app scaffold")
            HomeNavGraph(
                navController = navController,
                logout = logout,
                homeViewModel = viewModel,
                onNavigateTo = onNavigateTo
            )
        }
    }
}

@Composable
fun HomeContent(
    state: HomeUiState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            HeaderSection(state = state)
        }

        ConstraintLayout(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
                .background(colorResource(id = R.color.light_gray))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            val (activity, attendance) = createRefs()
            AttendanceSection(
                modifier = Modifier.constrainAs(attendance) {
                    top.linkTo(parent.top)
                }
            )

            ActivitySection(
                modifier = Modifier.constrainAs(activity) {
                    top.linkTo(attendance.bottom, margin = 12.dp)
                }
            )
        }

    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController = rememberNavController()) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        modifier = Modifier.height(60.dp)
    ) {
        lNavItem.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentDestination?.route == item.route,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.route,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    }
}

