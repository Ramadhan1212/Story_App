package com.ipoy.storyapp_v1.di

import android.content.Context
import com.ipoy.storyapp_v1.network.StoryAppRetrofitInstance
import com.ipoy.storyapp_v1.ui.data.StoriesRepository
import com.ipoy.storyapp_v1.ui.database.StoriesDatabase

object Injection {
    fun provideRepository(context: Context): StoriesRepository {
        val database = StoriesDatabase.getDatabase(context)
        val apiService = StoryAppRetrofitInstance.apiService
        return StoriesRepository(database, apiService!!)
    }
}