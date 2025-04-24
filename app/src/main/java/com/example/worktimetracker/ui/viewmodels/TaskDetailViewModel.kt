package com.example.worktimetracker.ui.viewmodels

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.presentation.util.TokenKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.get
import com.example.worktimetracker.data.remote.response.Task
import com.example.worktimetracker.domain.use_case.ReportUseCase
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
import java.io.File
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val savedStateHandle: SavedStateHandle,
    private val taskUseCase: TaskUseCase,
    private val reportUseCase: ReportUseCase
) : ViewModel() {

    private val token = context.dataStore.get(TokenKey, "")
    private val id = savedStateHandle.get<Int>("id")!!

    val isLoading = MutableStateFlow(false)
    val isUploading = MutableStateFlow(false)

    val task = MutableStateFlow<Task?>(null)

    val reportTitle = MutableStateFlow("")
    val reportDescription = MutableStateFlow("")

    init {
        isLoading.value = true
        viewModelScope.launch {
            fetchTaskDetail()
            isLoading.value = false
        }
    }

    fun onAction(action: TaskDetailUiAction) {
        when (action) {
            is TaskDetailUiAction.UploadReportFile -> {
                uploadReport(action.file)
            }
        }
    }

    private fun fetchTaskDetail() {
        viewModelScope.launch {
            taskUseCase.getTaskDetail(
                token = token,
                id = id
            )
                .suspendOnSuccess {
                    task.value = this.data.data
                }
                .suspendOnException {
                    Timber.d("Exception: %s", this.message())
                }
                .suspendOnFailure {
                    Timber.d("Failure: %s", this.message())
                }
        }
    }

    private fun uploadReport(file: File) {
        isUploading.value = true
        viewModelScope.launch {
            reportUseCase.uploadReportFile(
                token = token,
                title = reportTitle.value,
                description = reportDescription.value,
                taskId = id,
                file = file
            )
                .suspendOnSuccess {

                }
                .suspendOnFailure {
                    Timber.d("Failure: %s", this.message())
                }
                .suspendOnException {
                    Timber.d("Exception: %s", this.message())
                }

            isUploading.value = false
        }
    }
}

sealed interface TaskDetailUiAction {
    data class UploadReportFile(val file: File) : TaskDetailUiAction
}