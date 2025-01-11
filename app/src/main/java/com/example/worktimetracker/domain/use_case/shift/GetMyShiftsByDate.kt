package com.example.worktimetracker.domain.use_case.shift

import com.example.worktimetracker.data.remote.response.PagedDataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.domain.repository.ShiftRepository
import com.skydoves.sandwich.ApiResponse

class GetMyShiftsByDate (
    private val shiftRepository: ShiftRepository
    ) {
        suspend operator fun invoke(
            day: Int? = null,
            month: Int? = null,
            year: Int? = null,
            token: String,
            includeCheckRecord: Boolean = false,
        ) : ApiResponse<PagedDataResponse<List<Shift>>> {
            return shiftRepository.getMyShiftsByDate(day, month, year, token, includeCheckRecord)
        }
}