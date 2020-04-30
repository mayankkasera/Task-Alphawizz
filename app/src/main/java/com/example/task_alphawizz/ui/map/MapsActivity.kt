package com.example.task_alphawizz.ui.map

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.task_alphawizz.R
import com.example.task_alphawizz.api.DataHelper
import com.example.task_alphawizz.utils.PolyLineHelper.animatePolyLine
import com.example.task_alphawizz.utils.PolyLineHelper.decodePoly
import com.example.task_alphawizz.utils.createFactory
import com.example.task_alphawizz.utils.gone
import com.example.task_alphawizz.utils.visible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

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

            makeDirection(LatLng(23.2599, 77.4126), LatLng(22.7196, 75.8577))

        }


    }

    private fun makeDirection(StartLocation: LatLng, EndLocation: LatLng) {
        loader.visible()
        mapsViewModel.getDirection(
            "drivings",
            "less_driving",
            "${StartLocation.latitude},${StartLocation.longitude}",
            "${EndLocation.latitude},${EndLocation.longitude}",
            resources.getString(R.string.direction_key)
        )
    }


    private fun setObserver() {
        mapsViewModel.mutableLiveData.observe(this, Observer {
            when(it){
                is MapsState.Succes -> {
                    drowDirection(it.responce)
                    setMarkers(LatLng(23.2599, 77.4126), LatLng(22.7196, 75.8577))
                    loader.gone()
                }
                is MapsState.Failure -> {
                    loader.gone()
                    Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun drowDirection(response : String) {


        val polylineOptions: PolylineOptions
        val blacklineOptions: PolylineOptions
        val blackPolyLine: Polyline
        val greayPolyLine: Polyline
        var PolyLineList: List<LatLng> = ArrayList()

        try {
            val jsonObject = JSONObject(response)
            if (jsonObject.get("status") == "REQUEST_DENIED") {
                Toast.makeText(
                    this@MapsActivity,
                    "Enter billing acount Api key in string.xml",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val jsonArray: JSONArray = jsonObject.getJSONArray("routes")
                if (jsonArray.length() > 0) {
                    val rout = jsonArray.getJSONObject(0)
                    val legs = rout.getJSONArray("legs")
                    val obj = legs.getJSONObject(0)
                    val distance = obj.getJSONObject("distance")
                    val dis = distance.getString("text")
                    Log.i("dvxndvnkjx", "onResponse: $dis")
                    val duration = obj.getJSONObject("duration")
                    val dur = duration.getString("text")
                    Log.i("dvxndvnkjx", "onResponse: $dur")
                    val disarray = dis.split(" ").toTypedArray()
                    val durarray = dur.split(" ").toTypedArray()
                    for (i in 0 until jsonArray.length()) {
                        val route = jsonArray.getJSONObject(i)
                        val poly = route.getJSONObject("overview_polyline")
                        val polyline = poly.getString("points")
                        PolyLineList = decodePoly(polyline)
                        Log.d("nmdbv", PolyLineList.toString() + "")
                    }
                    //Adjusting bounds
                    val builder = LatLngBounds.Builder()
                    for (latLng in PolyLineList) {
                        builder.include(latLng)
                    }
                    val bounds = builder.build()
                    val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2)
                    polylineOptions = PolylineOptions()
                    polylineOptions.color(Color.GRAY)
                    polylineOptions.width(12f)
                    polylineOptions.startCap(SquareCap())
                    polylineOptions.endCap(SquareCap())
                    polylineOptions.jointType(JointType.ROUND)
                    greayPolyLine = mMap.addPolyline(polylineOptions)
                    blacklineOptions = PolylineOptions()
                    blacklineOptions.color(Color.BLACK)
                    blacklineOptions.width(12f)
                    blacklineOptions.startCap(SquareCap())
                    blacklineOptions.endCap(SquareCap())
                    blacklineOptions.jointType(JointType.ROUND)
                    blackPolyLine = mMap.addPolyline(blacklineOptions)
                    animatePolyLine(PolyLineList, greayPolyLine, blackPolyLine)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
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
