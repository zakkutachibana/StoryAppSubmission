package com.zak.storyappsubmission.ui.main

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zak.storyappsubmission.R
import com.zak.storyappsubmission.UserPreference
import com.zak.storyappsubmission.ViewModelFactory
import com.zak.storyappsubmission.databinding.ActivityMainBinding
import com.zak.storyappsubmission.ui.add.AddActivity
import com.zak.storyappsubmission.ui.login.LoginActivity
import com.zak.storyappsubmission.ui.map.MapsActivity
import com.zak.storyappsubmission.ui.story.StoryFragment

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        setViewModel()
        setAction()
//        getStoryViewModel()
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
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) { user ->
            token = "Bearer ${user.token}"
            getToken()
        }


        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                supportActionBar?.show()
                supportActionBar?.title = getString(R.string.greeting, user.name)
                switchFragment(StoryFragment())
            } else {
                val mainIntent = Intent(this, LoginActivity::class.java)
                startActivity(mainIntent)
                finish()
            }
        }
    }

    fun getToken() = token
    private fun setAction() {
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(
                intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle()
            )
        }
    }

//    fun getStoryViewModel(): StoryPagerViewModel {
//        val viewModel: StoryPagerViewModel by viewModels {
//            StoryViewModel.ViewModelStoryFactory(
//                this,
//                ApiConfig.getApiService(),
//                token
//            )
//        }
//        return viewModel
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                AlertDialog.Builder(this).apply {
                    setMessage("Apakah Anda yakin ingin Logout?")
                    setPositiveButton("Ya") { _, _ ->
                        mainViewModel.logout()
                        finish()
                    }
                    setNegativeButton("Tidak") { _, _ ->
                    }
                    create()
                    show()
                }
                return true
            }
            R.id.map -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(
                    intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle()
                )
            }
        }
        return true
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

}