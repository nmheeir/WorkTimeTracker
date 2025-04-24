package com.example.worktimetracker.data.remote.repo.impl

import com.example.worktimetracker.data.remote.api.TaskApi
import com.example.worktimetracker.data.remote.enums.ProjectStatus
import com.example.worktimetracker.data.remote.repo.TaskRepository
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Task
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
}