package com.example.worktimetracker.ui.viewmodels

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktimetracker.core.presentation.util.TokenKey
import com.example.worktimetracker.core.presentation.util.dataStore
import com.example.worktimetracker.core.presentation.util.get
import com.example.worktimetracker.data.remote.response.Report
import com.example.worktimetracker.data.remote.response.fakeUserProfileDto
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
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TaskReportViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val taskUseCase: TaskUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val token = context.dataStore.get(TokenKey, "")
    private val taskId = savedStateHandle.get<Int>("taskId")

    val reports = MutableStateFlow<List<Report>>(fakeReports)

    init {
        getReports()
    }

    private fun getReports() {
        viewModelScope.launch {
            taskUseCase.getReportsByTaskId(
                token = token,
                taskId = taskId!!
            )
                .suspendOnSuccess {
                    reports.value = this.data.data ?: fakeReports
                }
                .suspendOnFailure {
                    Timber.d("Failure %s", this.message())
                }
                .suspendOnException {
                    Timber.d("Exception: %s", this.message)
                }
        }
    }
}

val fakeReports = listOf(
    Report(
        id = 1,
        title = "Report 1",
        description = "Summary of completed tasks during Sprint 1.",
        taskId = 101,
        author = fakeUserProfileDto,
        reportUrl = reportLink,
        createdAt = LocalDateTime.parse("2024-04-01T10:00:00")
    ),
    Report(
        id = 2,
        title = "System Error Analysis",
        description = "Detailed analysis of the system outage on April 5.",
        taskId = 102,
        author = fakeUserProfileDto,
        reportUrl = reportLink,
        createdAt = LocalDateTime.parse("2024-04-06T14:30:00")
    ),
    Report(
        id = 3,
        title = "UI Design Report",
        description = "UI concepts and wireframes for the user management module.",
        taskId = 101,
        author = fakeUserProfileDto,
        reportUrl = reportLink,
        createdAt = LocalDateTime.parse("2024-04-10T09:15:00")
    ),
    Report(
        id = 4,
        title = "Login Test Cases",
        description = "List of test cases executed for the login functionality.",
        taskId = 103,
        author = fakeUserProfileDto,
        reportUrl = reportLink,
        createdAt = LocalDateTime.parse("2024-04-15T16:45:00")
    ),
    Report(
        id = 5,
        title = "Phase 1 Acceptance Report",
        description = "Internal acceptance summary for project phase 1.",
        taskId = 104,
        author = fakeUserProfileDto,
        reportUrl = reportLink,
        createdAt = LocalDateTime.parse("2024-04-20T11:20:00")
    )
)

const val reportLink =
    "https://mkranrxtwhxvvafxwomq.supabase.co/storage/v1/object/public/avatars/avatars_user_1.png"