package com.zak.storyappsubmission.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zak.storyappsubmission.UserModel
import com.zak.storyappsubmission.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}