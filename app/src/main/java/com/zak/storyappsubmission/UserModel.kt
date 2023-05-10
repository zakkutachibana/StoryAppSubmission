package com.zak.storyappsubmission

data class UserModel (
    val userId: String,
    val email: String,
    val password: String,
    val name: String,
    val token: String,
    val isLogin: Boolean
    )