package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.response.User
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    // TODO: sửa lại route
    @FormUrlEncoded
    @POST("/login")
    suspend fun login(
        @Field(value = "username") username: String,
        @Field(value = "password") password: String
    ) : Response<User>
}