package com.example.worktimetracker.domain.use_case.shift

import com.example.worktimetracker.data.remote.response.PagedDataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.data.remote.repo.ShiftRepository
import com.skydoves.sandwich.ApiResponse

class GetMyShift (
    private val shiftRepository: ShiftRepository
) {
    suspend operator fun invoke(
        start: String? = null,
        end: String? = null,
        token: String
    ) : ApiResponse<PagedDataResponse<List<Shift>>> {
        return shiftRepository.getMyShift(start, end, token)
    }
}