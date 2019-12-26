package com.example.todolist_ramkumartextiles.owners.views.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.todolist_ramkumartextiles.R
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
    private var googleMap: GoogleMap? = null
    private var userList = ArrayList<String>()

    @SuppressLint("MissingPermission")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)

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
        readUsers(object:
            FirebaseCallback
        {
            override fun onCallback(list: ArrayList<String>) {
                for(user in list) {
                    var refLoc = FirebaseDatabase.getInstance().getReference("Users").child(user)
                    var markerList=ArrayList<Marker>()
                    refLoc.addValueEventListener(object: ValueEventListener
                    {
                        override fun onCancelled(p0: DatabaseError) {
                            Toast.makeText(context,p0.toString(),Toast.LENGTH_LONG).show()
                        }
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            var lat:Double = 0.0
                            var lng:Double = 0.0
                            for(m in markerList)
                            {
                                m.remove()
                            }
                          for(ds in dataSnapshot.children)
                          {
                            if(ds.key == "latitude")
                               lat = ds.getValue(String::class.java)!!.toDouble()
                            if(ds.key == "longitude")
                               lng = ds.getValue(String::class.java)!!.toDouble()
                          }
                          Log.d("lat/lng",lat.toString()+lng.toString())
                          val latlng = LatLng(lat, lng)
                          val marker = googleMap.addMarker(MarkerOptions().position(latlng).title(user))
                            markerList.add(marker)
                        }
                    })
                }
            }
        })
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

    private fun readUsers(firebaseCallback: FirebaseCallback) {
        var ref = FirebaseDatabase.getInstance().getReference("Usernames")
        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList.clear()
                for (ds in dataSnapshot.children) {
                    val user = ds.getValue(String::class.java)
                    if (user != null) {
                        userList.add(user)
                    }
                }
                firebaseCallback.onCallback(userList)
             }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    private interface FirebaseCallback{
        fun onCallback(list:ArrayList<String>)
    }
}