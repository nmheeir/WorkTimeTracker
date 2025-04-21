package com.example.worktimetracker.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.worktimetracker.core.presentation.padding
import com.example.worktimetracker.core.presentation.ui.HideOnScrollComponent
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.ui.component.image.CircleImage
import com.example.worktimetracker.ui.navigation.Screens
import com.example.worktimetracker.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()


    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    val user by viewModel.user.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            if (user != null) {
                HomeTopBar(
                    user = user!!,
                    onNavigate = {
                        navController.navigate(it.route)
                    }
                )
            }
        }
    ) { pv ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
        ) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item(
                    key = "calendar"
                ) {
                    Row {
                        viewModel.currentWeekDates.map {
                            DateItem(
                                day = it.key,
                                weekday = it.value.dayOfWeek.name,
                                isSelected = false
                            )
                        }
                    }
                }
            }

            HideOnScrollComponent(lazyListState = lazyListState) {
                Button(
                    onClick = { navController.navigate(Screens.CheckScreen.route) }
                ) {
                    Text(text = "Go to Check")
                }
            }
        }
    }
}

@Composable
fun DateItem(day: String, weekday: String, isSelected: Boolean) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.Black

    Column(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = day,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
        Text(
            text = weekday,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}

@Composable
private fun HomeTopBar(
    user: User,
    onNavigate: (Screens) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.padding.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        CircleImage(imageUrl = user.avatarURL)

        Spacer(modifier = Modifier.width(12.dp))

        // Name and Title
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = user.userFullName,
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = user.designation,
                style = MaterialTheme.typography.labelMedium,
            )
        }

        // Notification Icon
        IconButton(
            onClick = {
                onNavigate(Screens.MyProfileScreen)
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = Color.Black
            )
        }
    }
}