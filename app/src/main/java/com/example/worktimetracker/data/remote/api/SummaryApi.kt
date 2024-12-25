package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.data.remote.response.PayCheck
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SummaryApi {

    @GET("Sumary/GetMyWorkTimeEachDay")
    suspend fun getMyWorkTimeEachDay(
        @Header("Authorization") token: String,
        @Query("start") start: Long,
        @Query("end") end: Long
    ) : ApiResponse<DataResponse<List<DayWorkTime>>>

    @GET("Sumary/GetMyTotalWorkTime")
    suspend fun getMyTotalWorkTime(
        @Header("Authorization") token: String,
        @Query("start") start: Long,
        @Query("end") end: Long
    ) : ApiResponse<DataResponse<Long>>

    @GET("Sumary/GetMyPayCheck")
    suspend fun getMyPayCheck(
        @Header("Authorization") token: String
    ) : ApiResponse<DataResponse<List<PayCheck>>>
}