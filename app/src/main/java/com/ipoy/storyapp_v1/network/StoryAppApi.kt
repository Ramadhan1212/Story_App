package com.ipoy.storyapp_v1.network

import com.ipoy.storyapp_v1.model.StoriesResponse
import com.ipoy.storyapp_v1.model.UserLoginResponse
import com.ipoy.storyapp_v1.model.UserRegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface StoryAppApi {

    @FormUrlEncoded
    @POST("login")
    fun getLoginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserLoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun createAccount(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserRegisterResponse>

    @GET("stories")
    suspend fun getAllListStoriesPager(
        @Header("Authorization") authHeader: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoriesResponse

    @Multipart
    @POST("stories")
    fun postStory(
        @Header("Authorization") authHeader: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<UserRegisterResponse> // reuse



}