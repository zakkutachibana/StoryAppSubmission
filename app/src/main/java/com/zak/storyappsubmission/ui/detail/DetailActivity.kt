package com.zak.storyappsubmission.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.zak.storyappsubmission.R
import com.zak.storyappsubmission.databinding.ActivityDetailBinding
import com.zak.storyappsubmission.withDateFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_IMG = "extra_img"
        const val EXTRA_UPLOAD = "extra_upload"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {
        val name = intent.getStringExtra(EXTRA_NAME)
        val desc = intent.getStringExtra(EXTRA_DESC)
        val img = intent.getStringExtra(EXTRA_IMG)
        val date = intent.getStringExtra(EXTRA_UPLOAD)



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
            Glide.with(binding.ivDetail)
                .load(img)
                .into(binding.ivDetail)
            binding.tvUsername.text = name
            binding.tvDescription.text = desc
        if (date != null) {
            binding.tvUploadDate.text = date.withDateFormat()
        }

    }
}