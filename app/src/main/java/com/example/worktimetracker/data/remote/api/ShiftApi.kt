package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.PagedDataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ShiftApi {
    @GET("Shift/getMyShifts")
    suspend fun getMyShift (
        @Query("start") start: String? = null,
        @Query("end") end: String? = null,
        @Header("Authorization") token: String
    ) : ApiResponse<PagedDataResponse<List<Shift>>>

    @GET("Shift/getMyShiftsByDate")
    suspend fun getMyShiftsByDate (
        @Query("day") day: Int? = null,
        @Query("month") month: Int? = null,
        @Query("year") year: Int? = null,
        @Query("includeCheckRecord") includeCheckRecord : Boolean = false,
        @Header("Authorization") token: String
    ) : ApiResponse<PagedDataResponse<List<Shift>>>
}