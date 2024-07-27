package com.example.worktimetracker.domain.use_case.user

data class UserUseCase(
    val getUserByUserName: GetUserByUserName,
    val updateUserProfile: UpdateUserProfile
)
