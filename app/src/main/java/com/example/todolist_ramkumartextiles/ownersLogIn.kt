package com.example.todolist_ramkumartextiles

import android.content.Intent
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
