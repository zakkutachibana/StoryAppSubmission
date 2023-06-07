package com.zak.storyappsubmission

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.zak.storyappsubmission.response.ListStoryItem
import com.zak.storyappsubmission.retrofit.ApiService

class StoryRepository(private val apiService: ApiService, private val token: String) {
    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token)
            }
        ).liveData
    }
}