package com.example.task_alphawizz.ui.map

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.task_alphawizz.R
import com.example.task_alphawizz.api.DataHelper
import com.example.task_alphawizz.utils.createFactory
import com.example.task_alphawizz.utils.gone
import com.example.task_alphawizz.utils.visible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var startMarker: Marker
    private lateinit var endMarker: Marker
    private lateinit var mapsViewModel: MapsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        init()
        setObserver()


    }

    private fun init() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val factory = MapsViewModel(DataHelper().drectionRepositoryI).createFactory()
        mapsViewModel = ViewModelProvider(this, factory).get(MapsViewModel::class.java)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapLoadedCallback {
            setMarkers(LatLng(23.2599, 77.4126), LatLng(22.7196, 75.8577))

            makeDirection(LatLng(23.2599, 77.4126), LatLng(22.7196, 75.8577))



        }


    }

    private fun makeDirection(StartLocation: LatLng, EndLocation: LatLng) {
        val s = "https://maps.googleapis.com/maps/api/directions/json?" +
                "mode=drivings&" +
                "transit_routing_preference=less_driving&" +
                "origin=" + StartLocation.latitude + "," + StartLocation.longitude + "&" +
                "destination=" + EndLocation.latitude + "," + EndLocation.longitude + "&" +
                "key=" + resources.getString(R.string.direction_key)

        loader.visible()
        mapsViewModel.getDirection(s)
    }


    private fun setObserver() {
        mapsViewModel.mutableLiveData.observe(this, Observer {
            Log.i("kdsjcn", "shdvcjds  : " + it.toString())
            when(it){
                is MapsState.Succes -> {
                    Log.i("dsjvchds",it.responce)
                    loader.gone()
                }
                is MapsState.Failure -> {
                    loader.gone()
                    Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
                }
            }
        })
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
