package com.example.todolist_ramkumartextiles.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.adapters.RecycleAdapt
import com.example.todolist_ramkumartextiles.fragment.CompletedFrag
import com.example.todolist_ramkumartextiles.fragment.ToDoFrag
import com.example.todolist_ramkumartextiles.models.TaskInformation
import com.example.todolist_ramkumartextiles.services.LocationService
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_to_do.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class EmployeeActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var username:String
    private lateinit var sharedPreferences:SharedPreferences

    private  val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        var selectedFragment: Fragment? = null

        when(item.itemId)
        {
            R.id.todo ->{
                selectedFragment = ToDoFrag()
            }
            R.id.completed ->{
                selectedFragment = CompletedFrag()
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_user, selectedFragment!!).commit()
        true
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)

        auth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)

        if(sharedPreferences.getBoolean("LoginStatus",false)) {
            val intent = Intent(this, LocationService::class.java)
            startService(intent)
        }

        this.username = intent.getStringExtra("username").toString()

        FirebaseMessaging.getInstance().subscribeToTopic("user_$username")
        if(auth.currentUser == null)
        {
            finish()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

        bottom_nav_us.setOnNavigationItemSelectedListener (mOnNavigationItemSelectedListener)
        val startFrag = ToDoFrag()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_user, startFrag).commit()

        textView.text = "Welcome  $username"

        logout.setOnClickListener {
            auth.signOut()

            startActivity(Intent(applicationContext, LoginActivity::class.java))
            val ref = FirebaseDatabase.getInstance().getReference("Users").child(username).child("token")
            ref.removeValue()
            val edit: SharedPreferences.Editor = sharedPreferences.edit()
            edit.putBoolean("LoginStatus",false)
            edit.apply()
            val intent = Intent(this,LocationService::class.java)
            stopService(intent)
            finish()
        }
    }
}