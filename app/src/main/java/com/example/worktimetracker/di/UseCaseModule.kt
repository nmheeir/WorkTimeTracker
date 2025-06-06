package com.example.worktimetracker.di

import com.example.worktimetracker.data.remote.repo.AuthRepository
import com.example.worktimetracker.data.remote.repo.CheckRepository
import com.example.worktimetracker.data.remote.repo.LogRepository
import com.example.worktimetracker.data.remote.repo.ProjectRepository
import com.example.worktimetracker.data.remote.repo.ReportRepository
import com.example.worktimetracker.data.remote.repo.ShiftRepository
import com.example.worktimetracker.data.remote.repo.SummaryRepository
import com.example.worktimetracker.data.remote.repo.TaskRepository
import com.example.worktimetracker.data.remote.repo.UserRepository
import com.example.worktimetracker.domain.use_case.GetReportsByTaskId
import com.example.worktimetracker.domain.use_case.GetTaskDetail
import com.example.worktimetracker.domain.use_case.MyProjects
import com.example.worktimetracker.domain.use_case.MyTasks
import com.example.worktimetracker.domain.use_case.ProjectUseCase
import com.example.worktimetracker.domain.use_case.ReportUseCase
import com.example.worktimetracker.domain.use_case.TaskUseCase
import com.example.worktimetracker.domain.use_case.UploadReportFile
import com.example.worktimetracker.domain.use_case.auth.AuthUseCase
import com.example.worktimetracker.domain.use_case.auth.Login
import com.example.worktimetracker.domain.use_case.auth.RequestPasswordReset
import com.example.worktimetracker.domain.use_case.auth.ResetPassword
import com.example.worktimetracker.domain.use_case.check.Check
import com.example.worktimetracker.domain.use_case.check.CheckUseCase
import com.example.worktimetracker.domain.use_case.log.CreateLog
import com.example.worktimetracker.domain.use_case.log.GetLogs
import com.example.worktimetracker.domain.use_case.log.LogUseCase
import com.example.worktimetracker.domain.use_case.shift.GetMyShift
import com.example.worktimetracker.domain.use_case.shift.GetMyShiftsByDate
import com.example.worktimetracker.domain.use_case.shift.ShiftUseCase
import com.example.worktimetracker.domain.use_case.summary.GetAttendanceRecord
import com.example.worktimetracker.domain.use_case.summary.GetMyPayCheck
import com.example.worktimetracker.domain.use_case.summary.GetTotalWorkTime
import com.example.worktimetracker.domain.use_case.summary.GetWorkTimeEachDay
import com.example.worktimetracker.domain.use_case.summary.SummaryUseCase
import com.example.worktimetracker.domain.use_case.user.GetUserActivity
import com.example.worktimetracker.domain.use_case.user.GetUserByUserName
import com.example.worktimetracker.domain.use_case.user.GetUserProfile
import com.example.worktimetracker.domain.use_case.user.UploadAvatar
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
    fun provideLoginUseCase(authRepository: AuthRepository): AuthUseCase {
        return AuthUseCase(
            login = Login(authRepository),
            resetPassword = ResetPassword(authRepository),
            requestPasswordReset = RequestPasswordReset(authRepository)
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCase(
            getUserByUserName = GetUserByUserName(userRepository),
            getUserProfile = GetUserProfile(userRepository),
            uploadAvatar = UploadAvatar(userRepository),
            getUserActivity = GetUserActivity(userRepository)
        )
    }

    @Provides
    @Singleton
    fun provideCheckUseCase(checkRepository: CheckRepository): CheckUseCase {
        return CheckUseCase(
            check = Check(checkRepository),
        )
    }

    @Provides
    @Singleton
    fun provideShiftUseCase(shiftRepository: ShiftRepository): ShiftUseCase {
        return ShiftUseCase(
            getMyShift = GetMyShift(shiftRepository),
            getShiftsByDate = GetMyShiftsByDate(shiftRepository)
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
    ): SummaryUseCase {
        return SummaryUseCase(
            getWorkTimeEachDay = GetWorkTimeEachDay(summaryRepository),
            getMyPayCheck = GetMyPayCheck(summaryRepository),
            getTotalWorkTime = GetTotalWorkTime(summaryRepository),
            getAttendanceRecord = GetAttendanceRecord(summaryRepository)
        )
    }

    @Provides
    @Singleton
    fun provideProjectUseCase(iProjectRepo: ProjectRepository): ProjectUseCase {
        return ProjectUseCase(
            myProjects = MyProjects(iProjectRepo)
        )
    }

    @Provides
    @Singleton
    fun provideTaskUseCase(iTaskRepo: TaskRepository): TaskUseCase {
        return TaskUseCase(
            myTasks = MyTasks(iTaskRepo),
            getTaskDetail = GetTaskDetail(iTaskRepo),
            getReportsByTaskId = GetReportsByTaskId(iTaskRepo)
        )
    }

    @Provides
    @Singleton
    fun provideReportUseCase(iReportRepo: ReportRepository): ReportUseCase {
        return ReportUseCase(
            uploadReportFile = UploadReportFile(iReportRepo)
        )
    }
}