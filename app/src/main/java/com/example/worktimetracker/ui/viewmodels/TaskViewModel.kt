package com.example.worktimetracker.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.domain.PagingState
import com.example.worktimetracker.core.presentation.util.TokenKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.get
import com.example.worktimetracker.data.remote.enums.ProjectStatus
import com.example.worktimetracker.data.remote.response.Task
import com.example.worktimetracker.domain.use_case.TaskUseCase
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val taskUseCase: TaskUseCase
) : ViewModel() {
    private val token = context.dataStore.get(TokenKey, "")

    val isLoading = MutableStateFlow(false)

    val taskStateMap = mutableStateMapOf<ProjectFilter, List<Task>>()
    val loadMoreStateMap = mutableStateMapOf<ProjectFilter, Boolean>()
    val pagingState = mutableStateMapOf<ProjectFilter, PagingState>().apply {
        ProjectFilter.entries.forEach { put(it, PagingState()) }
    }
    val filter = MutableStateFlow<ProjectFilter>(ProjectFilter.All)

    init {
        viewModelScope.launch {
            filter.collect {
                if (taskStateMap[it] == null) {
                    getMyTasks()
                }
            }
        }
    }

    private fun getMyTasks() {
        viewModelScope.launch {
            taskUseCase.myTasks(
                token = token,
                pageNumber = pagingState[filter.value]!!.pageNumber,
                pageSize = pagingState[filter.value]!!.pageSize,
                filter = filter.value.value
            )
                .suspendOnSuccess {
                    taskStateMap[filter.value] = this.data.data ?: emptyList()
                    loadMoreStateMap[filter.value] = this.data.pageNumber < this.data.totalPages
                }
                .suspendOnFailure {
                    Timber.d("Failure: $this")
                }
                .suspendOnException {
                    Timber.d("Exception: $this")
                }
        }
    }

    fun loadMore() {
        // Sử dụng getOrPut để đảm bảo luôn có PagingState cho filter hiện tại.
        val currentFilter = filter.value
        val currentPaging = pagingState.getOrPut(currentFilter) { PagingState() }

        // Cập nhật paging state và gán lại vào map.
        val newPaging = currentPaging.copy(pageNumber = currentPaging.pageNumber + 1)
        pagingState[currentFilter] = newPaging

        viewModelScope.launch {
            taskUseCase.myTasks(
                token = token,
                pageNumber = newPaging.pageNumber,
                pageSize = newPaging.pageSize,
                filter = currentFilter.value
            )
                .suspendOnSuccess {
                    val newTasks = this.data.data ?: emptyList()
                    val currentTasks = taskStateMap[currentFilter] ?: emptyList()
                    val updatedTasks = currentTasks.toMutableList().apply {
                        addAll(newTasks.filter { task -> none { it.id == task.id } })
                    }
                    taskStateMap[currentFilter] = updatedTasks

                    loadMoreStateMap[currentFilter] = this.data.pageNumber < this.data.totalPages
                }
                .suspendOnFailure {
                    Timber.d("Load more failure: ${this.message()}")
                }
                .suspendOnException {
                    Timber.d("Load more exception: ${this.throwable}")
                }
        }
    }
}

enum class ProjectFilter(val value: ProjectStatus?) {
    All(null),
    NotStarted(ProjectStatus.NotStarted),
    Completed(ProjectStatus.Completed),
    InProgress(ProjectStatus.InProgress),
    OnHold(ProjectStatus.OnHold),
    Cancelled(ProjectStatus.Cancelled)
}