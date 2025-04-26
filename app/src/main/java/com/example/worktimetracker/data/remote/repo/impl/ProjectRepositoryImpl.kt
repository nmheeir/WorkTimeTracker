package com.example.worktimetracker.data.remote.repo.impl

import com.example.worktimetracker.data.remote.api.ProjectApi
import com.example.worktimetracker.data.remote.enums.ProjectStatus
import com.example.worktimetracker.data.remote.repo.ProjectRepository

class ProjectRepositoryImpl(
    private val projectApi: ProjectApi
) : ProjectRepository {
    override suspend fun myProject(
        token: String,
        pageNumber: Int,
        pageSize: Int,
        filter: ProjectStatus?
    ) = projectApi.myProjects(token, pageNumber, pageSize, filter)
}