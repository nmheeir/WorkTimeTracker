package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.request.CreateLogRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Log
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LogApi {
    @POST("Log/createLog")
    suspend fun createLog(
        @Body log: CreateLogRequest,
        @Header("Authorization") token: String
    ): ApiResponse<DataResponse<Log>>

    @GET("Log/getLogs")
    suspend fun getLogs(
        @Header("Authorization") token: String
    ): ApiResponse<DataResponse<List<Log>>>
}