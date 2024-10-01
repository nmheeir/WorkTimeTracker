package com.example.worktimetracker.data.remote.repoImpl

import com.example.worktimetracker.data.remote.api.CheckApi
import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.domain.repository.CheckRepository
import com.example.worktimetracker.domain.result.ApiResult
import retrofit2.Response

class CheckRepositoryImpl (
    private var checkApi : CheckApi
) : CheckRepository {
    override suspend fun check(checkType: Int, token: String): ApiResult<DataResponse<Any>> {
        return try {
            val response: Response<DataResponse<Any>> =
                checkApi.check(checkType, "Bearer $token")

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

    override suspend fun getCheckWithTime(
        token: String,
        start: Long?,
        end: Long?
    ): ApiResult<DataResponse<List<Check>>> {
        return try {
            val response: Response<DataResponse<List<Check>>> =
                checkApi.getCheckWithUnixEpoch("Bearer $token", start, end)
            when(response.code()) {
                200 -> {
                    ApiResult.Success(response.body()!!)
                }
                else -> {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    val errorResponse = DataResponse<List<Check>>(null, errorBody, false)
                    ApiResult.Error(errorResponse)
                }
            }
        }
        catch (e :Exception) {
            return ApiResult.NetworkError(e.message!!)
        }
    }

    override suspend fun getCheckWithDate(token: String, year: Int?, month: Int?, day: Int?) : ApiResult<DataResponse<List<Check>>>
    {
        return try {
            val response: Response<DataResponse<List<Check>>> =
                checkApi.getCheckWithDate("Bearer $token", year, month, day)
            when(response.code()) {
                200 -> {
                    ApiResult.Success(response.body()!!)
                }
                else -> {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    val errorResponse = DataResponse<List<Check>>(null, errorBody, false)
                    ApiResult.Error(errorResponse)
                }
            }
        }
        catch (e :Exception) {
            return ApiResult.NetworkError(e.message!!)
        }
    }
}