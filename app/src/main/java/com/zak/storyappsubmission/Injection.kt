package com.zak.storyappsubmission

import com.zak.storyappsubmission.retrofit.ApiConfig

object Injection {
    fun provideRepository(token: String): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService, token)
    }
}
