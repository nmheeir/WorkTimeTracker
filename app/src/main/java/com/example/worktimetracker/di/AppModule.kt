package com.example.worktimetracker.di

import com.example.worktimetracker.data.remote.RemoteDataSource
import com.example.worktimetracker.data.remote.api.AuthApi
import com.example.worktimetracker.data.remote.api.CheckApi
import com.example.worktimetracker.data.remote.api.LogApi
import com.example.worktimetracker.data.remote.api.ProjectApi
import com.example.worktimetracker.data.remote.api.ReportApi
import com.example.worktimetracker.data.remote.api.ShiftApi
import com.example.worktimetracker.data.remote.api.SummaryApi
import com.example.worktimetracker.data.remote.api.TaskApi
import com.example.worktimetracker.data.remote.api.UserApi
import com.example.worktimetracker.data.remote.repo.impl.AuthRepositoryImpl
import com.example.worktimetracker.data.remote.repo.impl.CheckRepositoryImpl
import com.example.worktimetracker.data.remote.repo.impl.LogRepositoryImpl
import com.example.worktimetracker.data.remote.repo.impl.ShiftRepositoryImpl
import com.example.worktimetracker.data.remote.repo.impl.SummaryRepositoryImpl
import com.example.worktimetracker.data.remote.repo.impl.UserRepositoryImpl
import com.example.worktimetracker.data.remote.repo.AuthRepository
import com.example.worktimetracker.data.remote.repo.CheckRepository
import com.example.worktimetracker.data.remote.repo.LogRepository
import com.example.worktimetracker.data.remote.repo.ProjectRepository
import com.example.worktimetracker.data.remote.repo.ReportRepository
import com.example.worktimetracker.data.remote.repo.ShiftRepository
import com.example.worktimetracker.data.remote.repo.SummaryRepository
import com.example.worktimetracker.data.remote.repo.TaskRepository
import com.example.worktimetracker.data.remote.repo.UserRepository
import com.example.worktimetracker.data.remote.repo.impl.ProjectRepositoryImpl
import com.example.worktimetracker.data.remote.repo.impl.ReportRepositoryImpl
import com.example.worktimetracker.data.remote.repo.impl.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }

    @Provides
    @Singleton
    fun provideAuthApi(
        remoteDataSource: RemoteDataSource
    ): AuthApi {
        return remoteDataSource.buildApi(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(
        remoteDataSource: RemoteDataSource
    ): UserApi {
        return remoteDataSource.buildApi(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepo(
        userApi: UserApi
    ): UserRepository {
        return UserRepositoryImpl(userApi)
    }

    @Provides
    @Singleton
    fun provideCheckApi(
        remoteDataSource: RemoteDataSource
    ): CheckApi {
        return remoteDataSource.buildApi(CheckApi::class.java)
    }

    //Log
    @Provides
    @Singleton
    fun provideLogApi(remoteDataSource: RemoteDataSource): LogApi {
        return remoteDataSource.buildApi(LogApi::class.java)

    }

    @Provides
    @Singleton
    fun provideCheckRepo(
        checkApi: CheckApi
    ): CheckRepository {
        return CheckRepositoryImpl(checkApi)
    }

    @Provides
    @Singleton
    fun provideShiftApi(
        remoteDataSource: RemoteDataSource
    ): ShiftApi {
        return remoteDataSource.buildApi(ShiftApi::class.java)
    }

    @Provides
    @Singleton
    fun provideShiftRepo(
        shiftApi: ShiftApi
    ): ShiftRepository {
        return ShiftRepositoryImpl(shiftApi)
    }

    @Provides
    @Singleton
    fun provideLogRepo(
        logApi: LogApi
    ): LogRepository {
        return LogRepositoryImpl(logApi)
    }

    @Provides
    @Singleton
    fun provideSummaryApi(
        remoteDataSource: RemoteDataSource
    ): SummaryApi {
        return remoteDataSource.buildApi(SummaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSummaryRepo(
        summaryApi: SummaryApi
    ): SummaryRepository {
        return SummaryRepositoryImpl(summaryApi)
    }

    @Provides
    @Singleton
    fun provideProjectApi(
        remoteDataSource: RemoteDataSource
    ): ProjectApi {
        return remoteDataSource.buildApi(ProjectApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProjectRepo(projectApi: ProjectApi): ProjectRepository {
        return ProjectRepositoryImpl(projectApi)
    }

    @Provides
    @Singleton
    fun provideTaskApi(
        remoteDataSource: RemoteDataSource
    ): TaskApi {
        return remoteDataSource.buildApi(TaskApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskApi: TaskApi): TaskRepository {
        return TaskRepositoryImpl(taskApi)
    }

    @Provides
    @Singleton
    fun provideReportApi(remoteDataSource: RemoteDataSource): ReportApi {
        return remoteDataSource.buildApi(ReportApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReportRepository(reportApi: ReportApi): ReportRepository {
        return ReportRepositoryImpl(reportApi)
    }
}
