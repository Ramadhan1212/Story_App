package com.ipoy.storyapp_v1.ui.stories

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ipoy.storyapp_v1.di.Injection
import com.ipoy.storyapp_v1.model.ListStoryItem
import com.ipoy.storyapp_v1.ui.data.StoriesRepository

class StoriesViewModel(storyRepository: StoriesRepository, token: String): ViewModel() {

    val listStoryItem: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStory(token).cachedIn(viewModelScope)

}

class ViewModelFactory(private val context: Context, private val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoriesViewModel(Injection.provideRepository(context), token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}