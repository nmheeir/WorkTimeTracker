package com.example.worktimetracker.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.worktimetracker.data.local.db.entity.UserSession


@Dao
interface UserSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserSession(userSession: UserSession)

    @Query("SELECT * FROM UserSession WHERE username = :username")
    suspend fun getUserSession(username: String): UserSession?

    @Query("SELECT * FROM UserSession")
    suspend fun getAllUserSession(): List<UserSession>

    @Query("UPDATE UserSession SET avatarUrl = :newAvatar WHERE username = :username")
    suspend fun updateUserAvatar(username: String, newAvatar: String)
}