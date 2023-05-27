package com.ipoy.storyapp_v1.ui.gmaps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ipoy.storyapp_v1.connection.Connection
import com.ipoy.storyapp_v1.story.Stories
import com.ipoy.storyapp_v1.story.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GMapsViewModel : ViewModel() {

    val list = MutableLiveData<List<Stories>>()

    fun getLocationData(token: String){
        Connection.apiIns
            .locationStory("Bearer $token", 1)
            .enqueue(object : Callback<StoryResponse>{
                override fun onResponse(
                    call: Call<StoryResponse>,
                    response: Response<StoryResponse>
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body()?.list)
                        Log.i("response_Success", response.body()?.result.toString())
                    } else {
                        Log.i("response_failed", response.body()?.result.toString())
                    }
                }

                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                    Log.d("failed", t.message.toString())
                }
            })
    }


    fun getLocation(): LiveData<List<Stories>> {
        return list
    }
}