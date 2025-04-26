package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.enums.ProjectStatus
import com.example.worktimetracker.data.remote.response.PagedDataResponse
import com.example.worktimetracker.data.remote.response.Project
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ProjectApi {

    @GET("Project/my-projects")
    suspend fun myProjects(
        @Header("Authorization") token: String,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int,
        @Query("filter") filter: ProjectStatus? = null
    ): ApiResponse<PagedDataResponse<List<Project>>>
}