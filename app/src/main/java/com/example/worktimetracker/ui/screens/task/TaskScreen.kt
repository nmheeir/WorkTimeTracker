package com.example.worktimetracker.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.worktimetracker.core.presentation.padding
import com.example.worktimetracker.core.presentation.util.Gap
import com.example.worktimetracker.core.presentation.util.hozPadding
import com.example.worktimetracker.ui.component.ChipsRow
import com.example.worktimetracker.ui.component.item.TaskCardItem
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.viewmodels.ProjectFilter
import com.example.worktimetracker.ui.viewmodels.TaskViewModel

@Composable
fun TaskScreen(
    navController: NavHostController,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val taskFilter by viewModel.filter.collectAsStateWithLifecycle()
    val taskPage by remember(taskFilter) {
        derivedStateOf {
            taskFilter.let {
                viewModel.taskStateMap[it]
            }
        }
    }

    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.layoutInfo.visibleItemsInfo.any { it.key == "load_more" }
        }.collect { shouldShowLoadMore ->
            if (!shouldShowLoadMore) return@collect
            viewModel.loadMore()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.onBackground)
    ) {
        if (isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
            ) {
                item(
                    key = "project_summary"
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .hozPadding()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.extraSmall),
                            modifier = Modifier
                                .padding(vertical = MaterialTheme.padding.small)
                        ) {
                            Text(
                                text = "Summary of your work",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.hozPadding()
                            )
                            Text(
                                text = "Your current project progress",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.hozPadding()
                            )
                        }
                    }
                }

                item(
                    key = "filter_task"
                ) {
                    ChipsRow(
                        chips = listOf(
                            ProjectFilter.All to "All",
                            ProjectFilter.NotStarted to "Not Started",
                            ProjectFilter.Completed to "Completed",
                            ProjectFilter.InProgress to "In Progress",
                            ProjectFilter.OnHold to "On Hold",
                            ProjectFilter.Cancelled to "Cancelled"
                        ),
                        currentValue = taskFilter,
                        onValueUpdate = {
                            if (viewModel.filter.value != it) {
                                viewModel.filter.value = it
                            }
                            /*scope.launch {
                                lazyListState.animateScrollToItem(0)
                            }*/
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }


                items(
                    items = taskPage.orEmpty(),
                    key = { it.id }
                ) { task ->
                    TaskCardItem(
                        task = task,
                        onClick = {
                            navController.navigate("task_detail/${task.id}")
                        }
                    )
                    Gap(MaterialTheme.padding.small)
                }

                if (viewModel.loadMoreStateMap[taskFilter] == true) {
                    item(
                        key = "load_more"
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    item {
                        if (taskPage.isNullOrEmpty()) {
                            Text(
                                text = "No result found"
                            )
                        } else {
                            Text(
                                text = "No more result"
                            )
                        }
                    }
                }
            }
        }
    }
}