package com.example.todolist_ramkumartextiles.owners.views.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.owners.viewmodel.MapsViewModel
import com.example.todolist_ramkumartextiles.owners.viewmodel.MapsViewModelFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MapsFrag : Fragment(),OnMapReadyCallback{

    private lateinit var mMapView: MapView
    private lateinit var mapViewModel: MapsViewModel
    private var googleMap: GoogleMap? = null

    @SuppressLint("MissingPermission")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)

        mapViewModel = ViewModelProviders.of(this,MapsViewModelFactory()).get(MapsViewModel::class.java)
        mMapView = rootView.findViewById(R.id.mapView)
        mMapView.onCreate(savedInstanceState)

        mMapView.onResume() // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mMapView.getMapAsync(this)
        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        mapViewModel.addMarkers(googleMap)
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }

}