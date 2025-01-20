package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.request.CheckRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CheckApi {
    @POST("Check/check")
    suspend fun check(
        @Body checkRequest: CheckRequest,
        @Header("Authorization") token: String,
    ): ApiResponse<DataResponse<Any>>
}