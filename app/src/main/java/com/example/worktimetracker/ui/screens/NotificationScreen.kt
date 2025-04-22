package com.example.worktimetracker.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.worktimetracker.R
import com.example.worktimetracker.core.presentation.padding
import com.example.worktimetracker.ui.component.dialog.AlertDialog
import com.example.worktimetracker.ui.component.item.NotificationCardItem
import com.example.worktimetracker.ui.viewmodels.NotificationUiAction
import com.example.worktimetracker.ui.viewmodels.NotificationViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NotificationScreen(
    navController: NavHostController,
    viewModel: NotificationViewModel = hiltViewModel(),
) {

    val haptic = LocalHapticFeedback.current

    val notifications by viewModel.notifications.collectAsStateWithLifecycle()

    var inSelectMode by remember { mutableStateOf(false) }
    val selection = rememberSaveable(
        saver = listSaver<MutableList<Int>, Int>(
            save = { it.toList() },
            restore = { it.toMutableStateList() }
        )
    ) { mutableStateListOf() }
    val onExitSelectionMode = {
        inSelectMode = false
        selection.clear()
    }
    if (inSelectMode) {
        BackHandler(onBack = onExitSelectionMode)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (inSelectMode) {
                        Text(
                            pluralStringResource(
                                R.plurals.n_selected,
                                selection.size,
                                selection.size
                            )
                        )
                    } else {
                        Text(
                            text = "Notification"
                        )
                    }
                },
                navigationIcon = {
                    if (inSelectMode) {
                        IconButton(
                            onClick = onExitSelectionMode
                        ) {
                            Icon(painterResource(R.drawable.ic_close), null)
                        }
                    } else {
                        IconButton(
                            onClick = navController::navigateUp
                        ) {
                            Icon(Icons.AutoMirrored.Default.KeyboardArrowLeft, null)
                        }
                    }
                },
                actions = {
                    var showDeleteAllDialog by remember { mutableStateOf(false) }
                    var showMarkAllAsReadDialog by remember { mutableStateOf(false) }
                    IconButton(
                        onClick = {
                            showDeleteAllDialog = true
                        },
                        enabled = notifications.isNotEmpty()
                    ) {
                        Icon(Icons.Default.Delete, null)
                        if (showDeleteAllDialog && notifications.isNotEmpty()) {
                            AlertDialog(
                                title = "Delete all",
                                text = "Are you sure you want to delete all notifications?",
                                onDismiss = { showDeleteAllDialog = false },
                                onConfirm = {
                                    if (!inSelectMode) {
                                        viewModel.onAction(NotificationUiAction.DeleteAll)
                                    } else {
                                        viewModel.onAction(
                                            NotificationUiAction.DeleteMultipleNotifications(
                                                selection.toList()
                                            )
                                        )
                                        onExitSelectionMode()
                                    }
                                    showDeleteAllDialog = false
                                }
                            )
                        }
                    }

                    IconButton(
                        onClick = { showMarkAllAsReadDialog = true },
                        enabled = notifications.isNotEmpty()
                    ) {
                        Icon(painterResource(R.drawable.ic_check_circle), null)
                        if (showMarkAllAsReadDialog && notifications.isNotEmpty()) {
                            AlertDialog(
                                title = "Mark all as read",
                                onDismiss = { showMarkAllAsReadDialog = false },
                                onConfirm = {
                                    if (!inSelectMode) {
                                        viewModel.onAction(NotificationUiAction.MarkAllAsRead)
                                    } else {
                                        viewModel.onAction(
                                            NotificationUiAction.MarkMultipleAsRead(
                                                selection.toList()
                                            )
                                        )

                                        onExitSelectionMode()
                                    }
                                    showMarkAllAsReadDialog = false
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { contentPadding ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
            contentPadding = contentPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            if (notifications.isEmpty()) {
                item(
                    key = "no_data"
                ) {
                    Text(
                        text = "No data"
                    )
                }
            }

            items(
                items = notifications,
                key = { it.id }
            ) { notification ->
                val onCheckedChange: (Boolean) -> Unit = {
                    if (it) {
                        selection.add(notification.id)
                    } else {
                        selection.remove(notification.id)
                    }
                }

                NotificationCardItem(
                    notification = notification,
                    trailingContent = {
                        if (inSelectMode) {
                            Checkbox(
                                checked = notification.id in selection,
                                onCheckedChange = { selected ->
                                    onCheckedChange(selected)
                                }
                            )
                        } else if (notification.isRead) {
                            Icon(Icons.Default.Check, null)
                        } else {
                            TextButton(
                                onClick = {
                                    viewModel.onAction(
                                        NotificationUiAction.MarkAsRead(
                                            notification
                                        )
                                    )
                                }
                            ) {
                                Text(text = "Mark as read")
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .combinedClickable(
                            onClick = {
                                if (inSelectMode) {
                                    onCheckedChange(!selection.contains(notification.id))
                                }
                            },
                            onLongClick = {
                                if (!inSelectMode) {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    inSelectMode = true
                                    selection.add(notification.id)
                                }
                            }
                        )
                )
            }
        }
    }
}