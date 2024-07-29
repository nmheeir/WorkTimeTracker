package com.example.worktimetracker.domain.use_case.shift

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Shift
import com.example.worktimetracker.domain.repository.ShiftRepository
import com.example.worktimetracker.domain.result.ApiResult
import java.time.LocalDateTime

class GetMyShift (
    private val shiftRepository: ShiftRepository
) {
    suspend operator fun invoke(
        start: Long? = null,
        end: Long? = null,
        token: String
    ) : ApiResult<DataResponse<List<Shift>>> {
        return shiftRepository.getMyShift(start, end, token)
    }
}