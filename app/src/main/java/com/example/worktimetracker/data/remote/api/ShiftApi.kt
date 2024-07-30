package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Shift
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ShiftApi {
    @GET("Shift/getMyShifts")
    suspend fun getMyShift (
        @Query("start") start: Long? = null,
        @Query("end") end: Long? = null,
        @Header("Authorization") token: String
    ) : Response<DataResponse<List<Shift>>>
}