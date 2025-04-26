package com.example.worktimetracker.domain.use_case

import com.example.worktimetracker.data.remote.repo.ReportRepository
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.ReportInformation
import com.skydoves.sandwich.ApiResponse
import java.io.File

data class ReportUseCase(
    val uploadReportFile: UploadReportFile
)

class UploadReportFile(
    private val iReportRepo: ReportRepository
) {
    suspend operator fun invoke(
        token: String,
        title: String,
        description: String,
        taskId: Int,
        file: File
    ): ApiResponse<DataResponse<ReportInformation>> {
        return iReportRepo.upload(
            token = "Bearer $token",
            title = title,
            description = description,
            taskId = taskId,
            file = file
        )
    }
}