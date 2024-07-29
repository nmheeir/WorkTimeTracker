package com.example.worktimetracker.data.remote.repoImpl

import android.util.Log
import com.example.worktimetracker.data.remote.api.UserApi
import com.example.worktimetracker.data.remote.request.UserUpdateRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.domain.repository.UserRepository
import com.example.worktimetracker.domain.result.ApiResult

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {
    override suspend fun getUserByUsername(userName: String): ApiResult<DataResponse<User>> {
        return try {
            val response = userApi.getUserByUsername(userName)

            when (response.code()) {
                200 -> {
                    ApiResult.Success(response.body()!!)
                }

                else -> {
                    Log.d("login_error", response.message())
                    val errorResponse = DataResponse<User>(
                        _data = null,
                        _message = "Get user error: " + response.message(),
                        _success = false
                    )
                    ApiResult.Error(errorResponse)
                }
            }
        } catch (e: Exception) {
            return ApiResult.NetworkError(e.message!!)
        }
    }

    override suspend fun updateUseProfile(
        token: String,
        updateUser: UserUpdateRequest
    ): ApiResult<DataResponse<User>> {
        return try {
            val bearerToken = "Bearer $token"
            val response = userApi.updateUserProfile(token, updateUser)

            when (response.code()) {
                200 -> {
                    ApiResult.Success(response.body()!!)
                }
                else -> {
                    Log.d("login_error", response.message())
                    val errorResponse = DataResponse<User>(
                        _data = null,
                        _message = "Update user error: " + response.message(),
                        _success = false
                    )
                    ApiResult.Error(errorResponse)
                }
            }
        } catch (e: Exception) {
            return ApiResult.NetworkError(e.message!!)
        }
    }

    override suspend fun uploadAvatar(
        token: String,
        avatarUrl: String
    ): ApiResult<DataResponse<User>> {
        return try {
            val bearerToken = "Bearer $token"
            val response = userApi.uploadAvatar(bearerToken, avatarUrl)

            when (response.code()) {
                200 -> {
                    ApiResult.Success(response.body()!!)
                }
                else -> {
                    Log.d("login_error", response.message())
                    val errorResponse = DataResponse<User>(
                        _data = null,
                        _message = "Update user error: " + response.message(),
                        _success = false
                    )
                    ApiResult.Error(errorResponse)
                }
            }
        } catch (e: Exception) {
            return ApiResult.NetworkError(e.message!!)
        }
    }
}