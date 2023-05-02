package com.ipoy.storyapp_v1.injection

import android.content.Context
import com.ipoy.storyapp_v1.data.source.StoryRepository
import com.ipoy.storyapp_v1.data.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService(context)
        return StoryRepository(apiService)
    }
}