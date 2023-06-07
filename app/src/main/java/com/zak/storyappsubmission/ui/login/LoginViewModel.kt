package com.zak.storyappsubmission.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zak.storyappsubmission.retrofit.ApiConfig
import com.zak.storyappsubmission.UserPreference
import com.zak.storyappsubmission.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.viewModelScope
import com.zak.storyappsubmission.UserModel
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    companion object{
        private const val TAG = "LoginViewModel"
    }

    private val _loginStatus = MutableLiveData<LoginResponse>()
    val loginStatus: LiveData<LoginResponse> = _loginStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val error = MutableLiveData("")

    fun postLogin(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _loginStatus.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    response.errorBody()?.let {
                        val errorResponse = JSONObject(it.string())
                        val errorMessages = errorResponse.getString("message")
                        error.postValue(errorMessages)
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "onFailure (OF): ${t.message.toString()}")
                _isLoading.value = false
            }
        })
    }

    fun setUserPreference(userId: String, name: String, token: String) {
        viewModelScope.launch {
            pref.saveUser(
                UserModel(
                    userId,
                    name,
                    token,
                    true
                )
            )
        }
    }

}