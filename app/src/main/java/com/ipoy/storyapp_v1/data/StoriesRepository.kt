package com.ipoy.storyapp_v1.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.ipoy.storyapp_v1.connection.Query
import com.ipoy.storyapp_v1.database.StoriesDatabase
import com.ipoy.storyapp_v1.story.Stories

class StoriesRepository(private val storiesDatabase: StoriesDatabase, private val query: Query) {
    fun getStories(token: String): LiveData<PagingData<Stories>> {
        @OptIn(ExperimentalPagingApi::class)
        return  Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = StoriesRemoteMediator(storiesDatabase, query, token),
            pagingSourceFactory = {
                storiesDatabase.storiesDao().getAllStories()
            }
        ).liveData
    }
}