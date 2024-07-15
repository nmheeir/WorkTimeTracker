package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("Users/GetUserByUserName")
    suspend fun getUserByUsername(
        @Query("userName") userName: String
    ): Response<DataResponse<User>>
}