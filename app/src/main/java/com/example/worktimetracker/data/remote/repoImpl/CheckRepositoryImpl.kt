package com.example.worktimetracker.data.remote.repoImpl

import android.util.Log
import androidx.datastore.preferences.protobuf.Api
import com.example.worktimetracker.data.remote.api.CheckApi
import com.example.worktimetracker.data.remote.request.UserLoginRequest
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.domain.repository.CheckRepository
import com.example.worktimetracker.domain.result.ApiResult
import retrofit2.Response
import java.util.Objects

class CheckRepositoryImpl (
    private var checkApi : CheckApi
) : CheckRepository {
    override suspend fun check(checkType: Int, token: String): ApiResult<DataResponse<Any>> {
        return try {
            val response: Response<DataResponse<Any>> =
                checkApi.check(checkType, "Bearer $token")
            Log.d("Repo Error Check", response.toString())
            Log.d("Repo Error Check _ token", token)

            when(response.code()) {
                200 -> {
                    ApiResult.Success(response.body()!!)
                }
                else -> {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    val errorResponse = DataResponse<Any>(null, errorBody, false)
                    ApiResult.Error(errorResponse)
                }
            }
        }
        catch (e :Exception) {
            return ApiResult.NetworkError(e.message!!)
        }

    }
}