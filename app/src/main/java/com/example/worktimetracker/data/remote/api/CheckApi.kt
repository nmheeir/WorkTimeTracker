package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.PagedDataResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CheckApi {
    @POST("Check/check")
    suspend fun check(
        @Query("checkType") checkType : Int,
        @Header("Authorization") token: String,
    ): ApiResponse<DataResponse<Any>>
}