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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.adapters.RecycleAdapt
import com.example.todolist_ramkumartextiles.models.TaskInformation
import com.example.todolist_ramkumartextiles.services.LocationService
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_to_do.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ToDoActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private var items = ArrayList<TaskInformation>()
    private lateinit var adapter : RecycleAdapt
    private lateinit var username:String
    private lateinit var sharedPreferences:SharedPreferences

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
        //val username = "Kalit"
        if(auth.currentUser == null)
        {
            finish()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(username).child("tasks")

        textView.text = "Welcome  $username"

        logout.setOnClickListener {
            auth.signOut()

            startActivity(Intent(applicationContext, LoginActivity::class.java))

            val edit: SharedPreferences.Editor = sharedPreferences.edit()
            edit.putBoolean("LoginStatus",false)
            edit.apply()
            val intent = Intent(this,LocationService::class.java)
            stopService(intent)
            finish()
        }

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                items.clear()
                for (ds in dataSnapshot.children)
                {                    val item: TaskInformation? = ds.getValue(TaskInformation::class.java)
                    if (item != null) {
                        items.add(item)
                    }
                }
                var c = items.size
                var myRef = FirebaseDatabase.getInstance().getReference("Users").child(username)
                myRef.child("count").setValue(c)
                adapter = RecycleAdapt(items, applicationContext)
                recyclerView.layoutManager = LinearLayoutManager(this@ToDoActivity) as RecyclerView.LayoutManager?
                recyclerView.adapter= adapter
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        updateTask.setOnClickListener {
            for(item in this.items)
            {
                if(item.getStatus())
                {
                    val ref = databaseReference.child(item.getTaskId())
                    ref.removeValue()
                }
            }
        }
    }
}

