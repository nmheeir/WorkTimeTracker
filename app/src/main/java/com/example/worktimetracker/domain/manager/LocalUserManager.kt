package com.example.worktimetracker.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {

    suspend fun saveAppEntry()
    fun readAppEntry(): Flow<Boolean>
    suspend fun saveAccessToken(accessToken: String)
    suspend fun readAccessToken(): String
    suspend fun saveDeviceToken()
    suspend fun readDeviceToken(): String

    suspend fun clear()
}