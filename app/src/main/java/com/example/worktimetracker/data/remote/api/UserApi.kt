package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.request.UserUpdateRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.User
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserApi {

    @GET("Users/GetUserByUserName")
    suspend fun getUserByUsername(
        @Query("userName") userName: String
    ): ApiResponse<DataResponse<User>>

    @PUT("Users/uploadAvatar")
    suspend fun uploadAvatar(
        @Header("Authorization") token: String,
        @Query("avatarURL") avatarUrl: String
    ): ApiResponse<DataResponse<User>>
}