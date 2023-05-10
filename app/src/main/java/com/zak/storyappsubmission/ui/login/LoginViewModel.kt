package com.zak.storyappsubmission.ui.login

import android.util.JsonToken
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zak.storyapp.retrofit.ApiConfig
import com.zak.storyappsubmission.UserPreference
import com.zak.storyappsubmission.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.viewModelScope
import com.zak.storyappsubmission.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    companion object{
        private const val TAG = "SignupViewModel"
    }

    private val _loginStatus = MutableLiveData<LoginResponse>()
    val loginStatus: LiveData<LoginResponse> = _loginStatus

    fun postLogin(email: String, password: String) {
        val client = ApiConfig.getApiService().postLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    _loginStatus.value = response.body()
                    val userId = response.body()?.loginResult?.userId
                    saveUser(userId, )
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _loginStatus.value = response.body()
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "onFailure (OF): ${t.message.toString()}")
            }
        })
    }

     fun saveUser(userId : String, email: String, password: String, name: String, token: String) {
         viewModelScope.launch {
             pref.saveUser(
                 UserModel(
                     userId,
                     email,
                     password,
                     networkSignIn.loginResult.name,
                     networkSignIn.loginResult.token,
                     true
                 )
             )
         }
    }
}