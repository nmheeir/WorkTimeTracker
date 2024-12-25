package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.request.UserLoginRequest
import com.example.worktimetracker.data.remote.request.UserRegisterRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.skydoves.sandwich.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("Auth/login")
    suspend fun login(
        @Body user: UserLoginRequest
    ): ApiResponse<DataResponse<Token>>
}