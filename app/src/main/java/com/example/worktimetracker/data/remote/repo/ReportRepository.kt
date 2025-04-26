package com.example.worktimetracker.data.remote.repo

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.ReportInformation
import com.skydoves.sandwich.ApiResponse
import java.io.File

interface ReportRepository {
    suspend fun upload(
        token: String,
        title: String,
        description: String,
        taskId: Int,
        file: File
    ): ApiResponse<DataResponse<ReportInformation>>
}