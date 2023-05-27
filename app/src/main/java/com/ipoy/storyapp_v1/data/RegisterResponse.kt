package com.ipoy.storyapp_v1.data

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("error")
    val state: Boolean,

    @SerializedName("message")
    val result: String

)
