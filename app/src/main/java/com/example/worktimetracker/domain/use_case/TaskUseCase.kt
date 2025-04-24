package com.example.worktimetracker.domain.use_case

import com.example.worktimetracker.data.remote.enums.ProjectStatus
import com.example.worktimetracker.data.remote.repo.TaskRepository

data class TaskUseCase(
    val myTasks: MyTasks,
    val getTaskDetail: GetTaskDetail
)

class MyTasks(
    private val iTaskRepo: TaskRepository
) {
    suspend operator fun invoke(
        token: String,
        pageNumber: Int,
        pageSize: Int,
        filter: ProjectStatus? = null
    ) = iTaskRepo.myTasks("Bearer $token", pageNumber, pageSize, filter)
}

class GetTaskDetail(
    private val taskRepo: TaskRepository
) {
    suspend operator fun invoke(
        token: String,
        id: Int
    ) = taskRepo.getTaskDetail("Bearer $token", id)
}
