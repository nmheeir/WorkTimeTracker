package com.example.worktimetracker.di

import android.app.Application
import com.example.worktimetracker.data.manager.LocalUserManager
import com.example.worktimetracker.data.remote.RemoteDataSource
import com.example.worktimetracker.data.remote.api.AuthApi
import com.example.worktimetracker.data.remote.repoImpl.AuthRepositoryImpl
import com.example.worktimetracker.domain.repository.remote.AuthRepository
import com.example.worktimetracker.ui.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application) : LocalUserManager {
        return LocalUserManager(application)
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
    fun provideAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }
}