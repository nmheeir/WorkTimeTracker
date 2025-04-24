package com.example.worktimetracker.data.remote.repo

import com.example.worktimetracker.data.remote.enums.ProjectStatus
import com.example.worktimetracker.data.remote.response.PagedDataResponse
import com.example.worktimetracker.data.remote.response.Project
import com.skydoves.sandwich.ApiResponse

interface ProjectRepository {
    suspend fun myProject(
        token: String,
        pageNumber: Int,
        pageSize: Int,
        filter: ProjectStatus? = null
    ): ApiResponse<PagedDataResponse<List<Project>>>
}