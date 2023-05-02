package com.ipoy.storyapp_v1.ui.main.liststory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ipoy.storyapp_v1.data.source.StoryRepository
import com.ipoy.storyapp_v1.data.response.stories.Story


class ListStoryViewModel(storyRepository: StoryRepository): ViewModel() {
    val stories: LiveData<PagingData<Story>> = storyRepository.getStories().cachedIn(viewModelScope)
}