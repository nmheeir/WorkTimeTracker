package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.ReportInformation
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ReportApi {

    @Multipart
    @POST("Report/upload")
    suspend fun upload(
        @Header("Authorization") token: String,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("taskId") taskId: RequestBody,
        @Part reportFile: MultipartBody.Part
    ): ApiResponse<DataResponse<ReportInformation>>
}