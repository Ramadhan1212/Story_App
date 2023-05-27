package com.ipoy.storyapp_v1.connection

import com.ipoy.storyapp_v1.data.LoginResponse
import com.ipoy.storyapp_v1.data.RegisterResponse
import com.ipoy.storyapp_v1.story.FileUploadResponse
import com.ipoy.storyapp_v1.story.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Query

interface Query {

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun regisUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @GET("stories")
    suspend fun listStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse

    @Multipart
    @POST("stories")
    fun uploadStory(
        @Header("Authorization") token: String,
        @Part("description") description: String,
        @Part file: MultipartBody.Part,
        @Part("lat") lat: RequestBody?,
        @Part("lon") lon: RequestBody?
    ): Call<FileUploadResponse>

    @GET("stories")
    fun locationStory(
        @Header("Authorization") token: String,
        @Query("location") location: Int
    ): Call<StoryResponse>

}