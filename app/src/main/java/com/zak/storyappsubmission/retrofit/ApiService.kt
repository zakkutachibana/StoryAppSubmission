package com.zak.storyappsubmission.retrofit

import com.zak.storyappsubmission.response.LoginResponse
import com.zak.storyappsubmission.response.StatusResponse
import com.zak.storyappsubmission.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<StatusResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>


    @GET("stories?location=1")
    fun getStories(
        @Header("Authorization") token: String,
    ): Call<StoryResponse>

    @GET("stories")
    suspend fun getStoryList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Authorization") token: String
    ): StoryResponse
    @Multipart
    @POST("stories")
    fun postStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Double,
        @Part("lon") lon: Double
    ): Call<StatusResponse>
}