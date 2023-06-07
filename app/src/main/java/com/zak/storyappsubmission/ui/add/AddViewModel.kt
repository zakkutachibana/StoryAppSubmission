package com.zak.storyappsubmission.ui.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zak.storyappsubmission.retrofit.ApiConfig
import com.zak.storyappsubmission.UserModel
import com.zak.storyappsubmission.UserPreference
import com.zak.storyappsubmission.response.StatusResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddViewModel(private val pref: UserPreference) : ViewModel() {
    companion object{
        private const val TAG = "SignupViewModel"
    }

    private val _postStatus = MutableLiveData<StatusResponse>()
    val postStatus: LiveData<StatusResponse> = _postStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val error = MutableLiveData("")

    val latitude = MutableLiveData(0.0)
    val longitude = MutableLiveData(0.0)
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun postStory(token: String, image: MultipartBody.Part, description: RequestBody, lat: Double, lng: Double) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postStory(token, image, description, lat, lng)
        client.enqueue(object : Callback<StatusResponse> {
            override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _postStatus.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    response.errorBody()?.let {
                        val errorResponse = JSONObject(it.string())
                        val errorMessages = errorResponse.getString("message")
                        error.postValue(errorMessages)
                    }
                }
            }
            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                Log.e(TAG, "onFailure (OF): ${t.message.toString()}")
                _isLoading.value = false
            }
        })
    }
}