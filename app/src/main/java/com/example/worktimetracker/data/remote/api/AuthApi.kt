package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.data.remote.response.user.UserLoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("Users/loginByUserName")
    suspend fun login(
        @Body request: UserLoginRequest
    ): Response<DataResponse<Token>>
}