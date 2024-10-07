package com.example.worktimetracker.di

import android.app.Application
import com.example.worktimetracker.data.manager.LocalUserManagerImpl
import com.example.worktimetracker.domain.manager.LocalUserManager
import com.example.worktimetracker.domain.repository.AuthRepository

import com.example.worktimetracker.domain.repository.CheckRepository
import com.example.worktimetracker.domain.repository.ShiftRepository

import com.example.worktimetracker.domain.repository.LogRepository
import com.example.worktimetracker.domain.repository.SummaryRepository

import com.example.worktimetracker.domain.repository.UserRepository
import com.example.worktimetracker.domain.repository.WorkTimeRepository
import com.example.worktimetracker.domain.use_case.app_entry.AppEntryUseCase
import com.example.worktimetracker.domain.use_case.app_entry.ReadAppEntry
import com.example.worktimetracker.domain.use_case.app_entry.SaveAppEntry

import com.example.worktimetracker.domain.use_case.check.Check
import com.example.worktimetracker.domain.use_case.check.CheckUseCase
import com.example.worktimetracker.domain.use_case.check.GetCheckWithDate
import com.example.worktimetracker.domain.use_case.check.GetCheckWithUnixEpoch

import com.example.worktimetracker.domain.use_case.log.CreateLog
import com.example.worktimetracker.domain.use_case.log.GetLogs
import com.example.worktimetracker.domain.use_case.log.LogUseCase

import com.example.worktimetracker.domain.use_case.login.Login
import com.example.worktimetracker.domain.use_case.login.AuthUseCase
import com.example.worktimetracker.domain.use_case.login.Register
import com.example.worktimetracker.domain.use_case.shift.GetMyShift
import com.example.worktimetracker.domain.use_case.shift.GetMyShiftsInMonth
import com.example.worktimetracker.domain.use_case.shift.ShiftUseCase
import com.example.worktimetracker.domain.use_case.summary.GetMyPayCheck
import com.example.worktimetracker.domain.use_case.summary.GetTotalWorkTime
import com.example.worktimetracker.domain.use_case.user.GetUserByUserName
import com.example.worktimetracker.domain.use_case.user.UpdateUserProfile
import com.example.worktimetracker.domain.use_case.user.UploadAvatar
import com.example.worktimetracker.domain.use_case.user.UserUseCase
import com.example.worktimetracker.domain.use_case.summary.GetWorkTimeEachDay
import com.example.worktimetracker.domain.use_case.summary.SummaryUseCase
import com.example.worktimetracker.domain.use_case.work_time.WorkTimeUseCase
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
    fun provideLoginUseCase(authRepository: AuthRepository): AuthUseCase {
        return AuthUseCase(
            login = Login(authRepository),
            register = Register(authRepository)
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCase(
            getUserByUserName = GetUserByUserName(userRepository),
            updateUserProfile = UpdateUserProfile(userRepository),
            uploadAvatar = UploadAvatar(userRepository)
        )
    }

    @Provides
    @Singleton
    fun provideCheckUseCase(checkRepository: CheckRepository) : CheckUseCase {
        return CheckUseCase(
            check = Check(checkRepository),
            getCheckWithUnixEpoch = GetCheckWithUnixEpoch(checkRepository),
            getCheckWithDate = GetCheckWithDate(checkRepository)
        )
    }

    @Provides
    @Singleton
    fun provideShiftUseCase(shiftRepository: ShiftRepository) : ShiftUseCase {
        return ShiftUseCase(
            getMyShift = GetMyShift(shiftRepository),
            getMyShiftsInMonth = GetMyShiftsInMonth(shiftRepository)
            )
    }

    @Provides
    @Singleton
    fun provideLogUseCase(logRepository: LogRepository): LogUseCase {
        return LogUseCase(
            createLog = CreateLog(logRepository),
            getLogs = GetLogs(logRepository)
        )
    }

    @Provides
    @Singleton
    fun provideSummaryUseCase(
        summaryRepository: SummaryRepository
    ) : SummaryUseCase {
        return SummaryUseCase(
            getWorkTimeEachDay = GetWorkTimeEachDay(summaryRepository),
            getMyPayCheck = GetMyPayCheck(summaryRepository),
            getTotalWorkTime = GetTotalWorkTime(summaryRepository)
        )
    }

}