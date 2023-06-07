package com.zak.storyappsubmission.ui.map

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.zak.storyappsubmission.R
import com.zak.storyappsubmission.UserPreference
import com.zak.storyappsubmission.ViewModelFactory
import com.zak.storyappsubmission.databinding.ActivityMapsBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var mapsViewModel: MapsViewModel
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setViewModel()

    }

    private fun setViewModel() {
        mapsViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MapsViewModel::class.java]

        mapsViewModel.getUser().observe(this){user ->
            token = "Bearer ${user.token}"
            mapsViewModel.getStories(token)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val indonesia = LatLng(-0.7893, 113.9213)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(indonesia))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(4.0f))

        mapsViewModel.story.observe(this) { storyList ->
            for (story in storyList) {
                mMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            story.lat?.toDouble() ?: 0.0,
                            story.lon?.toDouble() ?: 0.0
                        )
                    ).title(story.name).snippet(story.description)
                )?.tag = story
            }
        }

        getMyLocation()

    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}