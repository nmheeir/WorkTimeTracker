package com.example.worktimetracker.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.worktimetracker.data.local.db.entity.CheckInfoEntity
import com.example.worktimetracker.data.local.db.entity.NotificationEntity
import com.example.worktimetracker.data.local.db.entity.UserSession
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserSession(userSession: UserSession)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: NotificationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(checkInfo: CheckInfoEntity)


    //    ================ Update ============================
    @Update
    suspend fun update(notifications: List<NotificationEntity>)

    @Update
    suspend fun update(notification: NotificationEntity)

    @Query("UPDATE notifications SET isRead = :isRead WHERE id IN (:notificationIds)")
    suspend fun updateByIds(notificationIds: List<Int>, isRead: Boolean)


    //    ================ Delete ============================
    @Delete
    suspend fun delete(notification: NotificationEntity)

    @Delete
    suspend fun delete(notifications: List<NotificationEntity>)

    // ==================== Query ============================
    @Query("DELETE FROM notifications WHERE id IN (:notificationIds)")
    suspend fun deleteByIds(notificationIds: List<Int>)


    @Query("SELECT * FROM UserSession WHERE username = :username")
    suspend fun getUserSession(username: String): UserSession?

    @Query("SELECT * FROM UserSession")
    suspend fun getAllUserSession(): List<UserSession>

    @Query("UPDATE UserSession SET avatarUrl = :newAvatar WHERE username = :username")
    suspend fun updateUserAvatar(username: String, newAvatar: String)

    @Query("SELECT * FROM notifications WHERE receivedUsername = :receivedUsername ORDER BY receivedAt DESC")
    fun notifications(receivedUsername: String): Flow<List<NotificationEntity>>
}