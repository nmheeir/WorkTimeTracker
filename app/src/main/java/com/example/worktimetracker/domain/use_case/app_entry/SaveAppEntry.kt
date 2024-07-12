package com.example.worktimetracker.domain.use_case.app_entry

import com.example.worktimetracker.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}