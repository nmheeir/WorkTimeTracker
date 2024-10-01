package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.data.remote.response.DataResponse
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
    ): Response<DataResponse<Any>>

    @GET("Check/getCheckByMyUserIdWithEpochUnix")
    suspend fun getCheckWithUnixEpoch(
        @Header("Authorization") token: String,
        @Query("start") start : Long? = null,
        @Query("end") end : Long? = null,
    ) : Response<DataResponse<List<Check>>>
    @GET("Check/getCheckByMyUserIdWithDate")
    suspend fun getCheckWithDate(
        @Header("Authorization") token: String,
        @Query("year") year : Int?,
        @Query("month") month : Int?,
        @Query("day") day : Int?
    ) : Response<DataResponse<List<Check>>>
}