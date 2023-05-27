package com.ipoy.storyapp_v1.ui.upload

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ipoy.storyapp_v1.connection.Connection
import com.ipoy.storyapp_v1.story.FileUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadVM : ViewModel() {
    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    private val _load = MutableLiveData<Boolean>()
    val load: LiveData<Boolean> = _load

    val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result

    fun upload(token: String, desc: String, img: MultipartBody.Part, lat: RequestBody?, lon: RequestBody?){
        Connection.apiIns
            .uploadStory("Bearer $token", desc, img,lat , lon)
            .enqueue(object : Callback<FileUploadResponse>{
                override fun onResponse(
                    call: Call<FileUploadResponse>,
                    response: Response<FileUploadResponse>
                ) {
                    _load.value = true
                    if (response.isSuccessful){
                        _result.value = response.body()?.message
                        _state.value = response.body()?.error

                        Log.i("response_success", response.body().toString())
                    } else {
                        _state.value = true
                        Log.i("response_failed", response.body().toString())
                    }
                    _load.value = false
                }

                override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                    _load.value = true
                    Log.d("Fail", t.message.toString())
                    _load.value = false
                }
            })
    }
}