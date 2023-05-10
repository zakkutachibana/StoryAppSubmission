package com.zak.storyappsubmission.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.zak.storyappsubmission.R
import com.zak.storyappsubmission.databinding.ActivityLoginBinding
import com.zak.storyappsubmission.databinding.ActivitySignupBinding
import com.zak.storyappsubmission.ui.main.MainActivity
import com.zak.storyappsubmission.ui.signup.SignupActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setAction()

    }
    private fun setAction() {
        binding.tvToSignup.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        binding.customButton.setOnClickListener{
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            when {

                email.isEmpty() -> {
                    binding.edLoginEmail.error = getString(R.string.empty_email)
                }
                password.isEmpty() -> {
                    binding.edLoginPassword.error = getString(R.string.empty_password)
                }
                else -> {
                    loginViewModel.postLogin(email, password)
                }
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}