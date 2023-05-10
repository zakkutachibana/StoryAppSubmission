package com.zak.storyappsubmission.ui.signup

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zak.storyapp.retrofit.ApiConfig
import com.zak.storyappsubmission.UserModel
import com.zak.storyappsubmission.UserPreference
import com.zak.storyappsubmission.response.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel(private val pref: UserPreference) : ViewModel() {
    companion object{
        private const val TAG = "SignupViewModel"
    }

    private val _registerStatus = MutableLiveData<RegisterResponse>()
    val registerStatus: LiveData<RegisterResponse> = _registerStatus

    fun postRegister(name: String, email: String, password: String) {
        val client = ApiConfig.getApiService().postRegister(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    _registerStatus.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _registerStatus.value = response.body()
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG, "onFailure (OF): ${t.message.toString()}")
            }
        })
    }


}