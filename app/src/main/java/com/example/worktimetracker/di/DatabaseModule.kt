package com.example.worktimetracker.di

import android.content.Context
import androidx.room.Room
import com.example.worktimetracker.data.local.db.AppDatabase
import com.example.worktimetracker.data.local.db.dao.UserSessionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserSessionDao(database: AppDatabase): UserSessionDao {
        return database.userSessionDao()
    }
}