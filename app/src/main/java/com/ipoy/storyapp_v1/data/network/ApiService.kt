package com.ipoy.storyapp_v1.data.network

import com.ipoy.storyapp_v1.data.response.login.LoginResponse
import com.ipoy.storyapp_v1.data.response.signup.SignUpResponse
import com.ipoy.storyapp_v1.data.response.stories.PostStoryResponse
import com.ipoy.storyapp_v1.data.response.stories.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun postSignUp(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SignUpResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ) : StoriesResponse


    @Multipart
    @POST("stories")
    suspend fun postStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): PostStoryResponse
}