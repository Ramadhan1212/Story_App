package com.ipoy.storyapp_v1.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ipoy.storyapp_v1.connection.SessionManager
import com.ipoy.storyapp_v1.data.StoriesRepository
import com.ipoy.storyapp_v1.story.Stories

class HomeViewModel(
    private val pref: SessionManager,
    private val storiesRepository: StoriesRepository
) : ViewModel() {

    fun showStories(token: String): LiveData<PagingData<Stories>> {
        return storiesRepository.getStories(token).cachedIn(viewModelScope)
    }


    fun getToken(): LiveData<String> {
        val tkn = pref.getToken().asLiveData()
        return tkn
    }


}

