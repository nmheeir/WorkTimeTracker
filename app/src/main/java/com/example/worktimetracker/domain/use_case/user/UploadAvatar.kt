package com.example.worktimetracker.domain.use_case.user

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.domain.repository.UserRepository
import com.example.worktimetracker.domain.result.ApiResult

class UploadAvatar(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(token: String, avatarUrl: String) : ApiResult<DataResponse<User>> {
        return userRepository.uploadAvatar(token, avatarUrl)
    }
}
