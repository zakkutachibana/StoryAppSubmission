package com.zak.storyappsubmission.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zak.storyappsubmission.retrofit.ApiConfig
import com.zak.storyappsubmission.UserPreference
import com.zak.storyappsubmission.response.StatusResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel(private val pref: UserPreference) : ViewModel() {
    companion object{
        private const val TAG = "SignupViewModel"
    }

    private val _registerStatus = MutableLiveData<StatusResponse>()
    val registerStatus: LiveData<StatusResponse> = _registerStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val error = MutableLiveData("")

    fun postRegister(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postRegister(name, email, password)
        client.enqueue(object : Callback<StatusResponse> {
            override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _registerStatus.postValue(response.body())
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