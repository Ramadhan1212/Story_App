package com.ipoy.storyapp_v1

import android.content.Context
import com.ipoy.storyapp_v1.connection.Connection
import com.ipoy.storyapp_v1.data.StoriesRepository
import com.ipoy.storyapp_v1.database.StoriesDatabase

object Injection {
    fun provideRepository(context: Context): StoriesRepository {
        val database = StoriesDatabase.getDatabase(context)
        val apiService = Connection.apiIns
        return StoriesRepository(database, apiService)
    }
}