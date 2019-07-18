package com.example.todolist_ramkumartextiles.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.todolist_ramkumartextiles.R
import kotlinx.android.synthetic.main.activity_main.*
import com.example.todolist_ramkumartextiles.services.GPSTracker
import com.example.todolist_ramkumartextiles.services.LocationService


class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("test","onCreate")

        sharedPreferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestLocationPermission()
        }

        butt_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        butt_register.setOnClickListener {

            startActivity(Intent(this, RegisterActivity::class.java))
        }
        butt_owners.setOnClickListener {

            startActivity(Intent(this, ownersLogIn::class.java))

        }
        if(sharedPreferences.getBoolean("LoginStatus",false)) {
            val intent = Intent(this, LocationService::class.java)
            startService(intent)
        }

    }


   override fun onStart() {
        super.onStart()
        Log.d("test","onStart")
   }

   override fun onResume() {
        super.onResume()
       if(sharedPreferences.getBoolean("LoginStatus",false)) {
           val intent = Intent(this, LocationService::class.java)
           startService(intent)
       }
       else
       {
            val intent = Intent(this, LocationService::class.java)
            stopService(intent)
       }

   }
   override fun onPause() {
        super.onPause()
        Log.d("test","onPause")
   }
}




//if(sharedPreferences.getBoolean("LoginStatus",false))
//        {
//            val intent = Intent(this, LocationService::class.java)
//            startService(intent)
//        }
//        else{
//            val intent = Intent(this, LocationService::class.java)
//            stopService(intent)
//        }





//        if(sharedPreferences.getBoolean("LoginStatus",false))
//         {
//           username = sharedPreferences.getString("username","").toString()
//           mGoogleApiClient = GoogleApiClient.Builder(this)
//                  .addConnectionCallbacks(this)
//                  .addOnConnectionFailedListener(this)
//                  .addApi(LocationServices.API)
//                  .build()
//
//           mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//         }

//            if(sharedPreferences.getBoolean("LoginStatus",false)) {
//                if (mGoogleApiClient!!.isConnected()) {
//                    mGoogleApiClient!!.disconnect()
//                }
//            }


//        if (mGoogleApiClient != null) {
//            mGoogleApiClient!!.connect()
//        }

//, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener