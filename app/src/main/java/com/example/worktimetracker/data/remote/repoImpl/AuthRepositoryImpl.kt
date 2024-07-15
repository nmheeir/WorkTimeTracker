package com.example.worktimetracker.data.remote.repoImpl

import android.util.Log
import com.example.worktimetracker.data.remote.api.AuthApi
import com.example.worktimetracker.data.remote.request.UserLoginRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.domain.repository.AuthRepository
import com.example.worktimetracker.domain.result.ApiResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {

    private val gson = Gson()

    override suspend fun login(username: String, password: String): ApiResult<DataResponse<Token>> {
        return try {
            Log.d("login", "$username $password")

            val response: Response<DataResponse<Token>> =
                authApi.login(UserLoginRequest(username, password))
            Log.d("login", response.toString())

            when (response.code()) {
                200 -> {
                    ApiResult.Success(response.body()!!)
                }
                400, 404 -> {
                    Log.d("login_error", response.message())
                    val errorBody = response.errorBody()?.string()
                    val type = object : TypeToken<DataResponse<Token>>() {}.type
                    val errorResponse: DataResponse<Token> = gson.fromJson(errorBody, type)
                    ApiResult.Error(errorResponse)
                }

                else -> {
                    Log.d("login_error", response.message())
                    val errorResponse = DataResponse<Token>(
                        _data = null,
                        _message = "Sign in error: " + response.message(),
                        _success = false
                    )
                    ApiResult.Error(errorResponse)
                }
            }
        } catch (e: Exception) {
            Log.d("login_exception", e.toString())
            val errorResponse = DataResponse<Token>(
                _data = null,
                _message = "Sign in error - lỗi local: " + e.message,
                _success = false
            )
            ApiResult.Error(errorResponse)
        }
    }
}