package com.ipoy.storyapp_v1.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ipoy.storyapp_v1.model.ListStoryItem
import com.ipoy.storyapp_v1.model.StoriesResponse
import com.ipoy.storyapp_v1.network.StoryAppRetrofitInstance
import retrofit2.Call
import retrofit2.Response

class MapsViewModel: ViewModel() {
    private var _mapsListStory = MutableLiveData<List<ListStoryItem>>()
    val mapsListStory: LiveData<List<ListStoryItem>> = _mapsListStory

    fun getListStoryWithMap(token: String) {
        StoryAppRetrofitInstance.apiService!!
            .getAllListStoriesWithMap("Bearer $token", "1")
            .enqueue(object: retrofit2.Callback<StoriesResponse> {
                override fun onResponse(
                    call: Call<StoriesResponse>,
                    response: Response<StoriesResponse>
                ) {
                    if(response.isSuccessful) {
                        _mapsListStory.postValue(response.body()?.listStory)
                    }
                }

                override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                    Log.e("ERROR", t.message.toString())
                }

            })
    }
}
