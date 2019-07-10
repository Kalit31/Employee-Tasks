package com.example.todolist_ramkumartextiles

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_owners.*

class ownersLogIn : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owners)

        var sharedPreferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("login",false))
        {
            finish()
            startActivity(Intent(applicationContext, TaskAct::class.java))
        }


        logino.setOnClickListener {
            val username = etUsernameO.text.toString().trim()
            val password = etPasswordO.text.toString().trim()

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
            {
                Toast.makeText(applicationContext,"Please enter all details", Toast.LENGTH_SHORT).show()
            }
            else
            {
                if(username == "test" && password == "test@123")
                {
                    finish()
                    val edit:SharedPreferences.Editor = sharedPreferences.edit()
                    edit.putBoolean("login",true)
                    edit.apply()
                    startActivity(Intent(applicationContext, TaskAct::class.java))
                }
                else {

                    Toast.makeText(applicationContext, "Login failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
