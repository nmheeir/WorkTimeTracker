package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.data.remote.response.UserActivityDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query
import java.time.LocalDateTime

interface UserApi {

    @GET("Users/GetUserByUserName")
    suspend fun getUserByUsername(
        @Query("userName") userName: String
    ): ApiResponse<DataResponse<User>>

    @GET("Users/profile")
    suspend fun profile(
        @Header("Authorization") token: String
    ): ApiResponse<DataResponse<User>>

    @PUT("Users/uploadAvatar")
    suspend fun uploadAvatar(
        @Header("Authorization") token: String,
        @Query("avatarURL") avatarUrl: String
    ): ApiResponse<DataResponse<User>>

    @GET("Users/activity")
    suspend fun getUserActivity(
        @Header("Authorization") token: String,
        @Query("start") start: LocalDateTime? = null,
        @Query("end") end: LocalDateTime? = null
    ): ApiResponse<DataResponse<UserActivityDto>>
}