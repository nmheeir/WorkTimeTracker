package com.example.worktimetracker.data.remote.repoImpl

import android.util.Log
import com.example.worktimetracker.data.remote.api.UserApi
import com.example.worktimetracker.data.remote.request.UserUpdateRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.domain.repository.UserRepository
import com.example.worktimetracker.domain.result.ApiResult
import com.skydoves.sandwich.ApiResponse

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {
    override suspend fun getUserByUsername(
        userName: String
    ): ApiResponse<DataResponse<User>> {
        return userApi.getUserByUsername(userName)
    }



    override suspend fun uploadAvatar(
        token: String,
        avatarUrl: String
    ): ApiResponse<DataResponse<User>> {
        return userApi.uploadAvatar(token, avatarUrl)
    }
}