package com.example.todolist_ramkumartextiles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        butt_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        butt_register.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        butt_owners.setOnClickListener {
            startActivity(Intent(this,ownersLogIn::class.java))
        }
    }
}
