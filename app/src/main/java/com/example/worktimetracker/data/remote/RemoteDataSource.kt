package com.example.worktimetracker.data.remote

import com.example.worktimetracker.BuildConfig
import com.example.worktimetracker.data.remote.adapters.EmployeeTypeAdapter
import com.example.worktimetracker.data.remote.adapters.LocalDateTimeAdapter
import com.example.worktimetracker.data.remote.adapters.LogStatusAdapter
import com.example.worktimetracker.data.remote.adapters.RoleAdapter
import com.example.worktimetracker.data.remote.enums.EmployeeType
import com.example.worktimetracker.data.remote.enums.LogStatus
import com.example.worktimetracker.data.remote.enums.Role
import com.google.gson.GsonBuilder
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RemoteDataSource @Inject constructor() {
    fun <Api> buildApi(
        api: Class<Api>
    ): Api {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapter(EmployeeType::class.java, EmployeeTypeAdapter())
            .registerTypeAdapter(Role::class.java, RoleAdapter())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .registerTypeAdapter(LogStatus::class.java, LogStatusAdapter())
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
            .create(api)
    }

}