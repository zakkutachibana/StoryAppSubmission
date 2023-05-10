package com.zak.storyappsubmission.retrofit

import com.zak.storyappsubmission.response.LoginResponse
import com.zak.storyappsubmission.response.RegisterResponse
import com.zak.storyappsubmission.response.StoryResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}