package com.zak.storyappsubmission.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zak.storyappsubmission.R
import com.zak.storyappsubmission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}