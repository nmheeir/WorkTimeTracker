package com.example.worktimetracker.data.remote.repoImpl

import com.example.worktimetracker.data.remote.api.ShiftApi
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.domain.repository.ShiftRepository
import com.example.worktimetracker.domain.result.ApiResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.time.LocalDateTime

class ShiftRepositoryImpl(
    private val shiftApi: ShiftApi
) : ShiftRepository {
    override suspend fun getMyShift(
        start: Long? ,
        end: Long? ,
        token : String
    ): ApiResult<DataResponse<List<Shift>>> {
        return try {
            val response: Response<DataResponse<List<Shift>>> =
                shiftApi.getMyShift(start, end, token)

            when(response.code()) {
                200 -> {
                    ApiResult.Success(response.body()!!)
                }
                else -> {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    val gson = Gson()
                    val type = object : TypeToken<DataResponse<List<Shift>>>() {}.type
                    val errorResponse: DataResponse<List<Shift>> = gson.fromJson(errorBody, type)
                    ApiResult.Error(errorResponse)
                }
            }
        }
        catch (e :Exception) {
            return ApiResult.NetworkError(e.message!!)
        }
    }

}