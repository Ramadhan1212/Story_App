package com.ipoy.storyapp_v1.ui.gmaps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.ipoy.storyapp_v1.R
import com.ipoy.storyapp_v1.connection.SessionManager
import com.ipoy.storyapp_v1.databinding.FragmentGmapsBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class GMapsFragment : Fragment() {

    private var _binding: FragmentGmapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var Gmaps: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val vm: GMapsViewModel by viewModels()

    private var location: ArrayList<LatLng>? = null
    private var userMap: ArrayList<String>? = null
    private lateinit var token : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGmapsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.fcv_maps) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private val callback = OnMapReadyCallback{ googleMap ->
        location = ArrayList()
        userMap = ArrayList()
        Gmaps = googleMap
        Gmaps.uiSettings.apply {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }
        getDeviceLocation()
        markStoryLocation()
        setHasOptionsMenu(true)
        setMapStyle()
    }

    private fun getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext().applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Gmaps.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    Gmaps.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8f))
                } else {
                    Toast.makeText(requireContext(), getString(R.string.please_activate_location_message), Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    private fun markStoryLocation() {

        token = getToken()
        vm.getLocationData(token)
        vm.getLocation().observe(viewLifecycleOwner){
            if (it != null){
                for (i in it.indices) {
                    location!!.add(LatLng(it[i].lat, it[i].lon))
                    userMap!!.add(it[i].name)

                    Gmaps.addMarker(
                        MarkerOptions()
                            .position(location!![i])
                            .title(userMap!![i])
                    )
                    Gmaps.animateCamera(CameraUpdateFactory.newLatLngZoom(location!![i], 15f))
                }
            }
        }
    }


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                getDeviceLocation()
            }
        }

    fun getToken() : String {
        val pref = SessionManager.get(requireContext().dataStore)
        var data: String = "empty"
        pref.getToken().asLiveData().observe(viewLifecycleOwner){
            data = it.toString()
        }
        return data
    }

    private fun setMapStyle() {
        try {
            val success =
                Gmaps.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
            if (!success) {
                Log.e("MapFragment", "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e("MapFragment", "Error: ", exception)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.normal_type -> {
                Gmaps.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.satellite_type -> {
                Gmaps.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_type -> {
                Gmaps.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            R.id.hybrid_type -> {
                Gmaps.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}