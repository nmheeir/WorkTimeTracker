package com.example.worktimetracker.domain.use_case.user

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.domain.repository.UserRepository
import com.skydoves.sandwich.ApiResponse

class GetUserByUserName(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userName: String
    ): ApiResponse<DataResponse<User>> {
        return userRepository.getUserByUsername(userName)
    }
}