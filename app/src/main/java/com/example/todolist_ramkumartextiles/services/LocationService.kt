package com.example.todolist_ramkumartextiles.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderApi
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LocationService : Service(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private var mGoogleAPIClient: GoogleApiClient? = null
    private var mLocationRequest: LocationRequest? = null
    private val mLocationProvider: FusedLocationProviderApi? = null
    private val UPDATE_INTERVAL = 10000
    private val FASTEST_INTERVAL = 2000
    private var mLocation: Location? = null
    private var mLocationManager: LocationManager? = null
    private val listener: com.google.android.gms.location.LocationListener? = null


    internal inner class LocationThread(var service_id: Int) : Runnable {


        override fun run() {
            mGoogleAPIClient!!.connect()
        }
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mGoogleAPIClient!!.isConnected) {
            mGoogleAPIClient!!.disconnect()
        }
    }

    override fun onCreate() {
        super.onCreate()

        mGoogleAPIClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()
        mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val thread = Thread(LocationThread(startId))
        thread.start()
        return Service.START_STICKY
    }

    override fun onConnected(bundle: Bundle?) {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        startLocationUpdates()
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleAPIClient)

        if (mLocation == null) {
            startLocationUpdates()
        }
        if (mLocation != null) {
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onLocationChanged(location: Location) {
        Log.d(
            "Background Location ",
            "::::***********Latitude: " + location.latitude + " Longitude: " + location.longitude
        )
        val sharedPreferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        val refLocation = FirebaseDatabase.getInstance().getReference("Users").child(username!!)
        refLocation.child("latitude").setValue(location.latitude.toString())
        refLocation.child("longitude").setValue(location.longitude.toString())
    }

    override fun onConnectionSuspended(i: Int) {
        mGoogleAPIClient!!.connect()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    protected fun startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL.toLong())
            .setFastestInterval(FASTEST_INTERVAL.toLong())
        // Request location updates
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
            mGoogleAPIClient,
            mLocationRequest, this
        )

    }

    fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

    }

    fun onProviderEnabled(provider: String) {

    }

    fun onProviderDisabled(provider: String) {

    }
}