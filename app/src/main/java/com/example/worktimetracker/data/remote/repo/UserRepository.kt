package com.example.worktimetracker.data.remote.repo

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.data.remote.response.UserActivityDto
import com.skydoves.sandwich.ApiResponse
import java.time.LocalDateTime

interface UserRepository {

    suspend fun getUserByUsername(
        userName: String
    ): ApiResponse<DataResponse<User>>

    suspend fun profile(
        token: String
    ): ApiResponse<DataResponse<User>>

    suspend fun uploadAvatar(
        token: String,
        avatarUrl: String
    ): ApiResponse<DataResponse<User>>

    suspend fun getUserActivity(
        token: String,
        start: LocalDateTime?,
        end: LocalDateTime?
    ) : ApiResponse<DataResponse<UserActivityDto>>
}