package com.zak.storyappsubmission.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zak.storyappsubmission.retrofit.ApiConfig
import com.zak.storyappsubmission.UserModel
import com.zak.storyappsubmission.UserPreference
import com.zak.storyappsubmission.response.ListStoryItem
import com.zak.storyappsubmission.response.StoryResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(private val pref: UserPreference) : ViewModel() {
    companion object{
        private const val TAG = "StoryViewModel"
    }

    private val _story = MutableLiveData<List<ListStoryItem>>()
    val story: LiveData<List<ListStoryItem>> = _story

    val error = MutableLiveData("")

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun getStories(token: String) {
        val client = ApiConfig.getApiService().getStories(token)
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                if (response.isSuccessful) {
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
            }
        })
    }
}