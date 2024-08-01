package com.example.worktimetracker.domain.use_case.work_time

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.DayWorkTime
import com.example.worktimetracker.domain.repository.WorkTimeRepository
import com.example.worktimetracker.domain.result.ApiResult

class GetWorkTimeEachDay (
    private val workTimeRepository: WorkTimeRepository
) {
    suspend operator fun invoke(token: String, start: Long, end: Long): ApiResult<DataResponse<List<DayWorkTime>>> {
        return workTimeRepository.getMyWorkTimeEachDay(token, start, end)
    }
}