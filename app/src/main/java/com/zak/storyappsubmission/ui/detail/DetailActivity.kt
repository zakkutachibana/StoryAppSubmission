package com.zak.storyappsubmission.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.zak.storyappsubmission.R
import com.zak.storyappsubmission.databinding.ActivityDetailBinding
import com.zak.storyappsubmission.databinding.ActivityMainBinding
import com.zak.storyappsubmission.response.ListStoryItem

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    companion object {
        const val EXTRA_STORY = "extra_story"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {
        val story = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_STORY, ListStoryItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_STORY)
        }
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        if (story != null) {
            supportActionBar?.title = story.id
            Glide.with(binding.ivDetail)
                .load(story.photoUrl)
                .into(binding.ivDetail)
            binding.tvUsername.text = story.name
            binding.tvDescription.text = story.description
            binding.tvUploadDate.text = story.createdAt
        }
    }
}