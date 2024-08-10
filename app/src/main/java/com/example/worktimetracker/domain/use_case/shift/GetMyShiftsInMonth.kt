package com.example.worktimetracker.domain.use_case.shift

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.domain.repository.ShiftRepository
import com.example.worktimetracker.domain.result.ApiResult

class GetMyShiftsInMonth (
    private val shiftRepository: ShiftRepository
    ) {
        suspend operator fun invoke(
            month: Int? = null,
            year: Int? = null,
            token: String
        ) : ApiResult<DataResponse<List<Shift>>> {
            return shiftRepository.getMyShiftsInMonth(month, year, token)
        }
}