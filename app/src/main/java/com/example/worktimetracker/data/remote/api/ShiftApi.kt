package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.response.PagedDataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.time.LocalDateTime

interface ShiftApi {
    @GET("Shift/getMyShifts")
    suspend fun getMyShift(
        @Query("start") start: LocalDateTime? = null,
        @Query("end") end: LocalDateTime? = null,
        @Header("Authorization") token: String
    ): ApiResponse<PagedDataResponse<List<Shift>>>

    @GET("Shift/getMyShiftsByDate")
    suspend fun getMyShiftsByDate(
        @Query("day") day: Int? = null,
        @Query("month") month: Int? = null,
        @Query("year") year: Int? = null,
        @Query("includeCheckRecord") includeCheckRecord: Boolean = false,
        @Header("Authorization") token: String
    ): ApiResponse<PagedDataResponse<List<Shift>>>
}