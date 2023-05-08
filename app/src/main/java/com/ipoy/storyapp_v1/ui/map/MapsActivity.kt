package com.ipoy.storyapp_v1.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ipoy.storyapp_v1.R
import com.ipoy.storyapp_v1.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var viewModel: MapsViewModel
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        token = intent.getStringExtra(TOKEN).toString()

        viewModel = ViewModelProvider(this)[MapsViewModel::class.java]
        getListMap(token)
        viewModel.mapsListStory.observe(this) {
            it?.forEach { item ->
                val place = LatLng(item.lat, item.lon)
                mMap.addMarker(MarkerOptions().position(place).title("${item.name}'s place"))
                Log.e("map", "${item.lat} ${item.lon}")
            }
        }
        val bandung = LatLng(-6.917464, 107.619125)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bandung))

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
    }

    fun getListMap(token: String) {
        viewModel.getListStoryWithMap(token)
    }
    companion object {
        const val TOKEN = "token"
    }
}