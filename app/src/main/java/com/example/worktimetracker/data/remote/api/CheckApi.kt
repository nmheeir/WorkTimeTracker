package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.request.UserLoginRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Objects

interface CheckApi {
    @POST("Check/check")
    suspend fun check(
        @Query("checkType") checkType : Int,
        @Header("Authorization") token: String,
    ): Response<DataResponse<Any>>
}