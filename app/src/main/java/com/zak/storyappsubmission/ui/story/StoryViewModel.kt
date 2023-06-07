package com.zak.storyappsubmission.ui.story

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zak.storyappsubmission.Injection
import com.zak.storyappsubmission.StoryRepository
import com.zak.storyappsubmission.response.ListStoryItem

class StoryViewModel(storyRepository: StoryRepository) : ViewModel() {

    val listStory: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStory().cachedIn(viewModelScope)

    class StoryViewModelFactory(private val token: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StoryViewModel(Injection.provideRepository(token)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}