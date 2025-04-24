package com.example.worktimetracker.domain.use_case

import com.example.worktimetracker.data.remote.enums.ProjectStatus
import com.example.worktimetracker.data.remote.repo.ProjectRepository


data class ProjectUseCase(
    val myProjects: MyProjects
)

class MyProjects(
    private val iProjectRepository: ProjectRepository
) {
    suspend operator fun invoke(
        token: String,
        pageNumber: Int,
        pageSize: Int,
        filter: ProjectStatus? = null
    ) = iProjectRepository.myProject("Bearer $token", pageNumber, pageSize, filter)
}