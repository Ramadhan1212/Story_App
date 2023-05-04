package com.ipoy.storyapp_v1.ui.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.ipoy.storyapp_v1.model.ListStoryItem
import com.ipoy.storyapp_v1.network.StoryAppApi
import com.ipoy.storyapp_v1.ui.database.StoriesDatabase

const val pageSize = 5
class StoriesRepository(private val storiesDatabase: StoriesDatabase, private val storyAppApi: StoryAppApi) {
    @OptIn(ExperimentalPagingApi::class)
    fun getStory(token: String): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize
            ),
            remoteMediator = StoriesRemoteMediator(storiesDatabase, storyAppApi, token),
            pagingSourceFactory = {
                storiesDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
}