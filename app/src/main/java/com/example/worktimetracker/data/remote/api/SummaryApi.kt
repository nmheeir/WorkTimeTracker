package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.response.AttendanceRecord
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.data.remote.response.PayCheck
import com.example.worktimetracker.data.remote.response.TotalWorkTime
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SummaryApi {

    @GET("Summary/GetMyWorkTimeEachDay")
    suspend fun getMyWorkTimeEachDay(
        @Header("Authorization") token: String,
        @Query("start") start: String? = null,
        @Query("end") end: String? = null,
    ) : ApiResponse<DataResponse<List<DayWorkTime>>>

    @GET("Summary/GetMyTotalWorkTime")
    suspend fun getMyTotalWorkTime(
        @Header("Authorization") token: String,
        @Query("start") start: String? = null,
        @Query("end") end: String? = null,
    ) : ApiResponse<DataResponse<TotalWorkTime>>

    @GET("Summary/GetMyPayCheck")
    suspend fun getMyPayCheck(
        @Header("Authorization") token: String
    ) : ApiResponse<DataResponse<List<PayCheck>>>

    @GET("Summary/GetEmployeeAttendanceRecord")
    suspend fun getAttendanceRecord (
        @Header("Authorization") token: String,
        @Query("start") start: String? = null,
        @Query("end") end: String? = null,
    ) : ApiResponse<DataResponse<AttendanceRecord>>
}