package com.example.worktimetracker.data.remote.api

import com.example.worktimetracker.data.remote.response.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

import com.example.worktimetracker.data.remote.response.DataResponse
import com.example.worktimetracker.data.remote.response.Token
import com.example.worktimetracker.data.remote.response.user.UserLoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("Users/loginByUserName")
    suspend fun login(
        @Field(value = "userName") username: String,
        @Field(value = "password") password: String
    ) : Response<ResponseBody>

    @GET("Users/test")
    suspend fun test() : Response<ResponseBody>
}