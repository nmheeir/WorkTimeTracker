package com.example.worktimetracker.di

import com.example.worktimetracker.data.remote.RemoteDataSource
import com.example.worktimetracker.data.remote.api.AuthApi
import com.example.worktimetracker.data.remote.repoImpl.AuthRepositoryImpl
import com.example.worktimetracker.domain.repository.AuthRepository
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
}