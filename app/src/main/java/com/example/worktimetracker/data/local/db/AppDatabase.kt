package com.example.worktimetracker.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.worktimetracker.data.local.db.dao.UserSessionDao
import com.example.worktimetracker.data.local.db.entity.UserSession

@Database(
    entities = [ UserSession::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userSessionDao(): UserSessionDao
}