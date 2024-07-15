package com.example.worktimetracker.di

import android.app.Application
import com.example.worktimetracker.data.manager.LocalUserManagerImpl
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.repository.AuthRepository
import com.example.worktimetracker.domain.repository.UserRepository
import com.example.worktimetracker.domain.use_case.app_entry.AppEntryUseCase
import com.example.worktimetracker.domain.use_case.app_entry.ReadAppEntry
import com.example.worktimetracker.domain.use_case.app_entry.SaveAppEntry
import com.example.worktimetracker.domain.use_case.login.Login
import com.example.worktimetracker.domain.use_case.login.LoginUseCase
import com.example.worktimetracker.domain.use_case.user.GetUserByUserName
import com.example.worktimetracker.domain.use_case.user.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager {
        return LocalUserManagerImpl(application)
    }

    @Provides
    @Singleton
    fun provideAppEntryUseCase(localUserManager: LocalUserManager): AppEntryUseCase {
        return AppEntryUseCase(
            saveAppEntry = SaveAppEntry(localUserManager),
            readAppEntry = ReadAppEntry(localUserManager),
        )
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(
            login = Login(authRepository)
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCase(
            getUserByUserName = GetUserByUserName(userRepository)
        )
    }

}