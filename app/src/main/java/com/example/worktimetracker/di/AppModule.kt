package com.example.worktimetracker.di

import com.example.worktimetracker.data.remote.RemoteDataSource
import com.example.worktimetracker.data.remote.api.AuthApi
import com.example.worktimetracker.data.remote.api.CheckApi
import com.example.worktimetracker.data.remote.api.LogApi
import com.example.worktimetracker.data.remote.api.ShiftApi
import com.example.worktimetracker.data.remote.api.SummaryApi
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
import com.example.worktimetracker.data.remote.repo.ShiftRepository
import com.example.worktimetracker.data.remote.repo.SummaryRepository
import com.example.worktimetracker.data.remote.repo.UserRepository
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
}
