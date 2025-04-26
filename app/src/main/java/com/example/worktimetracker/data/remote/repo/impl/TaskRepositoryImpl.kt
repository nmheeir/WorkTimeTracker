package com.example.worktimetracker.data.remote.repo.impl

import com.example.worktimetracker.data.remote.api.TaskApi
import com.example.worktimetracker.data.remote.enums.ProjectStatus
import com.example.worktimetracker.data.remote.repo.TaskRepository
import com.example.worktimetracker.data.remote.response.PagedDataResponse
import com.example.worktimetracker.data.remote.response.Report
import com.skydoves.sandwich.ApiResponse

class TaskRepositoryImpl(
    private val taskApi: TaskApi
) : TaskRepository {
    override suspend fun myTasks(
        token: String,
        pageNumber: Int,
        pageSize: Int,
        filter: ProjectStatus?
    ) = taskApi.myTasks(token, pageNumber, pageSize, filter)

    override suspend fun getTaskDetail(
        token: String,
        id: Int
    ) = taskApi.getTaskDetail(token, id)

    override suspend fun getReportsByTaskId(
        token: String,
        taskId: Int
    ) = taskApi.getReportsByTaskId(token, taskId)
}