package com.example.worktimetracker.domain.use_case.user

import com.example.worktimetracker.data.remote.repo.UserRepository
import java.time.LocalDateTime

class GetUserActivity(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        token: String,
        start: LocalDateTime? = null,
        end: LocalDateTime? = null
    ) = userRepository.getUserActivity("Bearer $token", start, end)
}