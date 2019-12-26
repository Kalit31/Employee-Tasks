package com.example.todolist_ramkumartextiles.owners.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.todolist_ramkumartextiles.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_info.*

class InfoAct : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)


        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width * .55).toInt(), (height * .1).toInt())

        val username = intent.getStringExtra("username")

        val databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(username)

        databaseReference.addValueEventListener(object : ValueEventListener
        {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                    val email = p0.child("email").getValue(String::class.java)
                    val password = p0.child("password").getValue(String::class.java)
                tV_info.text = "Email: $email\nPasssword: $password"
              }
           }
        )
    }
}
