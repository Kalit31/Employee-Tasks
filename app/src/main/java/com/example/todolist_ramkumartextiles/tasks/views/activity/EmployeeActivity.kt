package com.example.todolist_ramkumartextiles.tasks.views.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.services.LocationService
import com.example.todolist_ramkumartextiles.auth.views.LoginActivity
import com.example.todolist_ramkumartextiles.di.AppModule
import com.example.todolist_ramkumartextiles.tasks.views.fragments.CompletedFrag
import com.example.todolist_ramkumartextiles.tasks.views.fragments.ToDoFrag
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_to_do.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class EmployeeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var username:String
    private lateinit var sharedPreferences:SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)

        auth = FirebaseAuth.getInstance()
        sharedPreferences = AppModule(application).providesSharedPreferences(application)

       if(sharedPreferences.getBoolean("PREF_KEY_LOGIN_STATUS",false)) {
            val intent = Intent(this, LocationService::class.java)
            startService(intent)
        }

        this.username = sharedPreferences.getString("PREF_KEY_CURRENT_USER_NAME","User").toString()
        if(auth.currentUser == null)        {
            finish()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

        FirebaseMessaging.getInstance().subscribeToTopic("user_$username")

        bottom_nav_us.setOnNavigationItemSelectedListener (mOnNavigationItemSelectedListener)
        val startFrag = ToDoFrag()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_user, startFrag).commit()

        textView.text = "Welcome  $username"

        logout.setOnClickListener {
            auth.signOut()
            AppModule(application).providesSharedPreferences(application).edit().putBoolean("PREF_KEY_LOGIN_STATUS",false).apply()
            AppModule(application).providesSharedPreferences(application).edit().putString("PREF_KEY_CURRENT_USER_NAME","user").apply()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }
    }

    private  val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment? = null
        when(item.itemId)
        {
            R.id.todo -> {
                selectedFragment = ToDoFrag()
            }
            R.id.completed -> {
                selectedFragment = CompletedFrag()
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_user, selectedFragment!!).commit()
        true
    }
}