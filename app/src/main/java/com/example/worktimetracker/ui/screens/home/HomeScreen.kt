package com.example.worktimetracker.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
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
import com.example.worktimetracker.ui.navigation.MainNavGraph
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.navigation.lNavItem
import com.example.worktimetracker.ui.screens.home.activity_section.ActivitySection
import com.example.worktimetracker.ui.screens.home.attendance_section.AttendanceSection
import com.example.worktimetracker.ui.screens.home.header_section.HeaderSection
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.util.exampleUser

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        val paddingValues = it
        MainNavGraph(navController = navController)
    }
}

@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
//                .padding(it)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            HeaderSection(user = exampleUser)
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
fun BottomNavigationBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        lNavItem.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentDestination?.route == item.title,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.title) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    }
}
