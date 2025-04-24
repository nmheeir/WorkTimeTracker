package com.example.worktimetracker.data.remote.repo

import com.example.worktimetracker.data.remote.enums.ProjectStatus
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.PagedDataResponse
import com.example.worktimetracker.data.remote.response.Task
import com.skydoves.sandwich.ApiResponse

interface TaskRepository {
    suspend fun myTasks(
        token: String,
        pageNumber: Int,
        pageSize: Int,
        filter: ProjectStatus?
    ) : ApiResponse<PagedDataResponse<List<Task>>>

    suspend fun getTaskDetail(
        token: String,
        id: Int
    ) : ApiResponse<DataResponse<Task>>
}