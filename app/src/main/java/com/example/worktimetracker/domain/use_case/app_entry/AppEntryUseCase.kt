package com.example.worktimetracker.domain.use_case.app_entry

data class AppEntryUseCase(
    val saveAppEntry: SaveAppEntry,
    val readAppEntry: ReadAppEntry
)