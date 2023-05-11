package com.zak.storyappsubmission.ui.signup

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.zak.storyappsubmission.R
import com.zak.storyappsubmission.UserPreference
import com.zak.storyappsubmission.ViewModelFactory
import com.zak.storyappsubmission.databinding.ActivitySignupBinding

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var signupViewModel: SignupViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        setViewModel()
        setAction()
    }
    private fun setView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    private fun setViewModel() {
        signupViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SignupViewModel::class.java]

        signupViewModel.registerStatus.observe(this) {
            if (!it.error) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        signupViewModel.error.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setAction() {

        binding.tvToLogin.setOnClickListener {
            finish()
        }

        binding.customButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            when {
                name.isEmpty() -> {
                    binding.edRegisterName.error = getString(R.string.empty_name)
                }
                email.isEmpty() -> {
                    binding.edRegisterEmail.error = getString(R.string.empty_email)
                }
                password.isEmpty() -> {
                    binding.edRegisterPassword.error = getString(R.string.empty_password)
                }
                else -> {
                    signupViewModel.postRegister(name, email, password)
                }
            }
        }
    }
}