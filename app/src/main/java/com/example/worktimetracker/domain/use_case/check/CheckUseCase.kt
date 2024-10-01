package com.example.worktimetracker.domain.use_case.check

data class CheckUseCase (
    var check : Check,
    var getCheckWithUnixEpoch: GetCheckWithUnixEpoch,
    var getCheckWithDate: GetCheckWithDate
)