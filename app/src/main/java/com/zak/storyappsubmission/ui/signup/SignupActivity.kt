package com.zak.storyappsubmission.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
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
        setAnimation()
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

        signupViewModel.isLoading.observe(this) {
            showLoading(it)
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
    private fun setAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title =
            ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(250)
        val name =
            ObjectAnimator.ofFloat(binding.layoutRegisterName, View.ALPHA, 1f).setDuration(250)
        val email =
            ObjectAnimator.ofFloat(binding.layoutRegisterEmail, View.ALPHA, 1f).setDuration(250)
        val password =
            ObjectAnimator.ofFloat(binding.layoutRegisterPassword, View.ALPHA, 1f).setDuration(250)
        val signIn = ObjectAnimator.ofFloat(binding.customButton, View.ALPHA, 1f).setDuration(250)
        val signInMessage =
            ObjectAnimator.ofFloat(binding.tvToLogin, View.ALPHA, 1f).setDuration(250)

        AnimatorSet().apply {
            playSequentially(
                title, name, email, password, signIn, signInMessage
            )
            startDelay = 500
            start()
        }
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}