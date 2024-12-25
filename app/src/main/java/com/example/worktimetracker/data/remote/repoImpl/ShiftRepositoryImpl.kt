package com.example.worktimetracker.data.remote.repoImpl

import android.util.Log
import com.example.worktimetracker.data.remote.api.ShiftApi
import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.domain.repository.ShiftRepository
import com.example.worktimetracker.domain.result.ApiResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response
import java.time.LocalDateTime

class ShiftRepositoryImpl(
    private val shiftApi: ShiftApi
) : ShiftRepository {
    override suspend fun getMyShift(
        start: Long? ,
        end: Long? ,
        token : String
    ): ApiResponse<DataResponse<List<Shift>>> {
        return shiftApi.getMyShift(start, end, token)
    }

    override suspend fun getMyShiftsInMonth(
        month: Int?,
        year: Int?,
        token: String
    ): ApiResponse<DataResponse<List<Shift>>> {
        return shiftApi.getMyShiftsInMonth(month, year, token)
    }

}