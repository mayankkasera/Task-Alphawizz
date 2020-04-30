package com.example.task_alphawizz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var startMarker: Marker
    private lateinit var endMarker: Marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapLoadedCallback {
            setMarkers(LatLng(23.2599, 77.4126), LatLng(22.7196, 75.8577))
        }


    }

    private fun setMarkers(StartLocation: LatLng, EndLocstion: LatLng) {
        mMap.clear()

        startMarker = mMap.addMarker(MarkerOptions()
                .position(StartLocation)
                .title("Start location"))

        endMarker = mMap.addMarker(MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .position(LatLng(EndLocstion.latitude, EndLocstion.longitude))
                .title("End location"))

        val builder = LatLngBounds.Builder()
        builder.include(EndLocstion)
        builder.include(StartLocation)
        val bounds = builder.build()

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(mMap.cameraPosition.zoom - 1.5f))
    }

}
