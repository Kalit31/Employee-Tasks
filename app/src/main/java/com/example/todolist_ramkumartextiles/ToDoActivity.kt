package com.example.todolist_ramkumartextiles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_to_do.*

class ToDoActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser == null)
        {
            finish()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
        val user = auth.currentUser
        if (user != null) {
            textView.text = "Welcome" + user.email
        }
        logout.setOnClickListener {
            auth.signOut()
            finish()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }
}
