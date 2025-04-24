package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.enums.ProjectStatus
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.PagedDataResponse
import com.example.worktimetracker.data.remote.response.Task
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface TaskApi {

    @GET("Task/my-tasks")
    suspend fun myTasks(
        @Header("Authorization") token: String,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int,
        @Query("filter") filter: ProjectStatus? = null
    ): ApiResponse<PagedDataResponse<List<Task>>>

    @GET("Task/{id}")
    suspend fun getTaskDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): ApiResponse<DataResponse<Task>>
}