package com.zak.storyappsubmission.ui.signup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.protobuf.Empty
import androidx.lifecycle.ViewModelProvider
import com.zak.storyappsubmission.R
import com.zak.storyappsubmission.UserModel
import com.zak.storyappsubmission.UserPreference
import com.zak.storyappsubmission.ViewModelFactory
import com.zak.storyappsubmission.databinding.ActivitySignupBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var signupViewModel: SignupViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setViewModel()
        setAction()
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
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
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
                    signupViewModel.postRegister(email, password, name)
                }
            }
        }
    }
}