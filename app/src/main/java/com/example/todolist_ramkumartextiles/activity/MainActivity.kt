package com.example.todolist_ramkumartextiles.activity

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.todolist_ramkumartextiles.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }

    private fun requestLocationPermission()
    {
        //if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION))
        //{}
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
    }

}
