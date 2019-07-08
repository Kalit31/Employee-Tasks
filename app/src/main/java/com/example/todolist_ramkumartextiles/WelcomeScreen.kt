package com.example.todolist_ramkumartextiles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.postDelayed

class WelcomeScreen : AppCompatActivity() {

    private val SPLASH_TIME_OUT = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        Handler().postDelayed(Runnable {
            kotlin.run {
                finish()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        }, SPLASH_TIME_OUT.toLong())

    }
}
