package com.example.worktimetracker.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.worktimetracker.R
import com.example.worktimetracker.core.ext.format2
import com.example.worktimetracker.core.ext.parseMinute
import com.example.worktimetracker.core.presentation.padding
import com.example.worktimetracker.core.presentation.ui.HideOnScrollComponent
import com.example.worktimetracker.core.presentation.util.hozPadding
import com.example.worktimetracker.data.remote.enums.CheckType
import com.example.worktimetracker.data.remote.response.CheckInfo
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.ui.component.background.LinearBackground
import com.example.worktimetracker.ui.component.item.ShiftCardItem
import com.example.worktimetracker.ui.component.image.CircleImage
import com.example.worktimetracker.ui.component.preferences.PreferenceEntry
import com.example.worktimetracker.ui.navigation.Screens
import com.example.worktimetracker.ui.screens.home.components.HomeOptionItem
import com.example.worktimetracker.ui.screens.home.components.HomeOptionItemData
import com.example.worktimetracker.ui.screens.home.components.listHomeOption
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.viewmodels.HomeViewModel
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.now
import kotlinx.datetime.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    val user by viewModel.user.collectAsStateWithLifecycle()
    val currentWeekDates by viewModel.currentWeekDates.collectAsStateWithLifecycle()
    val checkInfos by viewModel.checkInfos.collectAsStateWithLifecycle()
    val shifts by viewModel.todayShifts.collectAsStateWithLifecycle()

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (user != null) {
                item(
                    key = "top_bar"
                ) {
                    HomeTopBar(
                        user = user!!,
                        onNavigate = {
                            navController.navigate(it.route)
                        }
                    )
                }
            }

            item(
                key = "calendar"
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.mediumSmall),
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    currentWeekDates.fastForEach {
                        DateItem(
                            day = it.format(DateTimeFormatter.ofPattern("dd")),
                            weekday = it.dayOfWeek.getDisplayName(
                                TextStyle.SHORT,
                                Locale.ENGLISH
                            ),
                            isSelected = it.dayOfMonth == LocalDate.now().dayOfMonth
                        )
                    }
                }
            }

            //Shift
            item(
                key = "shift_header"
            ) {
                PreferenceEntry(
                    title = { Text(text = "Shift", color = Color.White) },
                    trailingContent = {
                        Icon(Icons.AutoMirrored.Default.ArrowForwardIos, null, tint = Color.White)
                    }
                )
                if (shifts.isEmpty()) {
                    Text(text = "You don't have any shift today", color = Color.White)
                }
            }

            items(
                items = shifts,
                key = { it.id }
            ) { shift ->
                ShiftCardItem(shift = shift, modifier = Modifier.hozPadding())
            }

            //Activity
            item(
                key = "activity"
            ) {
                PreferenceEntry(
                    title = { Text(text = "Activity", color = Color.White) },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                )
                if (checkInfos.isEmpty()) {
                    Text(text = "No Activity", color = Color.White)
                }
            }

            items(
                items = checkInfos,
            ) { checkInfo ->
                CheckInfoCardItem(
                    checkInfo = checkInfo,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }

        HideOnScrollComponent(lazyListState = lazyListState) {
            Button(
                shape = MaterialTheme.shapes.small,
                onClick = { navController.navigate(Screens.CheckScreen.route) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.regularSurface,
                    contentColor = AppTheme.colors.onRegularSurface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .hozPadding()
            ) {
                Text(text = "Go to Check")
            }
        }
    }
}

@Composable
fun DateItem(
    day: String,
    weekday: String,
    isSelected: Boolean
) {
    val backgroundColor =
        if (isSelected) AppTheme.colors.regularSurface else Color.Transparent
    val textColor = if (isSelected) AppTheme.colors.onRegularSurface else Color.White

    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(backgroundColor)
            .padding(MaterialTheme.padding.small),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.extraSmall)
    ) {
        Text(
            text = day,
            style = MaterialTheme.typography.bodyLarge,
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
    modifier: Modifier = Modifier,
    user: User,
    onNavigate: (Screens) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
        modifier = modifier
            .clickable {
                onNavigate(Screens.ProfileScreen)
            }
            .padding(MaterialTheme.padding.mediumSmall)
    ) {
        CircleImage(
            imageUrl = user.avatarUrl ?: "",
            size = 48.dp
        )
        Column(
            modifier = Modifier
                .weight(1f)

        ) {
            Text(
                text = user.userName,
                color = Color.White
            )
            Text(
                text = user.department,
                color = Color.White
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small)
        ) {
            var showBottomSheet by remember { mutableStateOf(false) }
            IconButton(
                onClick = { showBottomSheet = true }
            ) {
                Icon(Icons.Default.MoreHoriz, null, tint = Color.White)
            }

            if (showBottomSheet) {
                HomeBottomSheet(
                    onDismiss = { showBottomSheet = false },
                    onNavigate = onNavigate
                )
            }

            IconButton(
                onClick = {
                    onNavigate(Screens.Notification)
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onNavigate: (Screens) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        LinearBackground {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.mediumSmall),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                items(
                    items = listHomeOption
                ) { option ->
                    HomeOptionItem(
                        item = option,
                        onClick = { onNavigate(it) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CheckInfoCardItem(
    modifier: Modifier = Modifier,
    checkInfo: CheckInfo
) {
    Card(
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.regularSurface
        ),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
            modifier = Modifier.padding(MaterialTheme.padding.small)
        ) {
            val iconRes =
                if (checkInfo.type == CheckType.CheckIn) R.drawable.ic_login else R.drawable.ic_logout
            val iconTint =
                if (checkInfo.status == "Approved") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = iconTint
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = checkInfo.type.value,
                    color = Color.White
                )
                Text(
                    text = checkInfo.time.format2(),
                    color = Color.White
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = checkInfo.time.parseMinute(),
                    color = Color.White
                )
                Text(
                    text = checkInfo.status,
                    color = Color.White
                )
            }
        }
    }
}