package com.example.worktimetracker.data.remote.repoImpl

import android.util.Log
import com.example.worktimetracker.data.remote.api.AuthApi
import com.example.worktimetracker.data.remote.request.UserLoginRequest
import com.example.worktimetracker.data.remote.request.UserRegisterRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.domain.repository.AuthRepository
import com.example.worktimetracker.domain.result.ApiResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun login(
        username: String,
        password: String,
        deviceToken: String
    ): ApiResponse<DataResponse<Token>> {
        return authApi.login(UserLoginRequest(username, password, deviceToken))
    }
}