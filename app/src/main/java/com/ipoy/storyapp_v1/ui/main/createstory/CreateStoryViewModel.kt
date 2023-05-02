package com.ipoy.storyapp_v1.ui.main.createstory

import androidx.lifecycle.ViewModel
import com.ipoy.storyapp_v1.data.source.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreateStoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postStory(file: MultipartBody.Part, description: RequestBody) = storyRepository.postStory(file, description)
}