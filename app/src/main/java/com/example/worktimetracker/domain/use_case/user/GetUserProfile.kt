package com.example.worktimetracker.domain.use_case.user

import com.example.worktimetracker.data.remote.repo.UserRepository

class GetUserProfile(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(token: String) =
        userRepository.profile("Bearer $token")
}