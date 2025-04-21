package com.example.worktimetracker.data.remote.repo.impl

import com.example.worktimetracker.data.remote.api.UserApi
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.data.remote.repo.UserRepository
import com.skydoves.sandwich.ApiResponse

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {
    override suspend fun getUserByUsername(
        userName: String
    ): ApiResponse<DataResponse<User>> {
        return userApi.getUserByUsername(userName)
    }

    override suspend fun profile(token: String) =
        userApi.profile(token)

    override suspend fun uploadAvatar(
        token: String,
        avatarUrl: String
    ): ApiResponse<DataResponse<User>> {
        return userApi.uploadAvatar(token, avatarUrl)
    }
}