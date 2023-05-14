package com.zak.storyappsubmission.ui.story

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zak.storyapp.retrofit.ApiConfig
import com.zak.storyappsubmission.response.ListStoryItem
import com.zak.storyappsubmission.response.StoryResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryViewModel : ViewModel() {
    companion object{
        private const val TAG = "StoryViewModel"
    }
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _storyStatus = MutableLiveData<StoryResponse>()
    val storyStatus: LiveData<StoryResponse> = _storyStatus

    private val _story = MutableLiveData<List<ListStoryItem>>()
    val story: LiveData<List<ListStoryItem>> = _story

    val error = MutableLiveData("")


    fun getStories(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getStories(token)
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _storyStatus.postValue(response.body())
                    _story.value = response.body()?.listStory
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    response.errorBody()?.let {
                        val errorResponse = JSONObject(it.string())
                        val errorMessages = errorResponse.getString("message")
                        error.postValue(errorMessages)
                    }
                }
            }
            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Log.e(TAG, "onFailure (OF): ${t.message.toString()}")
                _isLoading.value = false
            }
        })
    }
}