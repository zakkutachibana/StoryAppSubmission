package com.zak.storyappsubmission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zak.storyappsubmission.ui.login.LoginViewModel
import com.zak.storyappsubmission.ui.signup.SignupViewModel

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
//            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
//                MainViewModel(pref) as T
//            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}