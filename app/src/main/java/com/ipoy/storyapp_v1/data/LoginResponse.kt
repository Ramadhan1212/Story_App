package com.ipoy.storyapp_v1.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("error")
    val state: Boolean,

    @SerializedName("loginResult")
    val result: Session
)
