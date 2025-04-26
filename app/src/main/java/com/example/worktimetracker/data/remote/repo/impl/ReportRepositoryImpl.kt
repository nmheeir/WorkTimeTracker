package com.example.worktimetracker.data.remote.repo.impl

import com.example.worktimetracker.data.remote.api.ReportApi
import com.example.worktimetracker.data.remote.repo.ReportRepository
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.ReportInformation
import com.skydoves.sandwich.ApiResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.File

class ReportRepositoryImpl(
    private val reportApi: ReportApi
) : ReportRepository {
    override suspend fun upload(
        token: String,
        title: String,
        description: String,
        taskId: Int,
        file: File
    ): ApiResponse<DataResponse<ReportInformation>> {
        Timber.d(
            "title: $title, description: $description, taskId: $taskId, file: ${file.name}"
        )
        val title = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val description = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val taskId = taskId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val fileBody = file.asRequestBody("application/octet-stream".toMediaType())
        val filePart = MultipartBody.Part.createFormData("reportFile", file.name, fileBody)

        return reportApi.upload(
            token = token,
            title = title,
            description = description,
            taskId = taskId,
            reportFile = filePart
        )
    }
}