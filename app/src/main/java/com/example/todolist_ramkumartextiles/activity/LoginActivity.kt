package com.example.todolist_ramkumartextiles.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.todolist_ramkumartextiles.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var username = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null)
        {
            finish()
            val todDoIntent = Intent(applicationContext, ToDoActivity::class.java)
            var sharedPreferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)
            todDoIntent.putExtra("username",sharedPreferences.getString("username",""))
            startActivity(todDoIntent)
        }

        login.setOnClickListener {
            val username = etUsernameL.text.toString()
            val email = emailL.text.toString()
            val password = etPasswordL.text.toString()
            //
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)|| TextUtils.isEmpty((username)))
            {
                Toast.makeText(applicationContext,"Please enter all details", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            finish()
                            var sharedPreferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)
                            val edit: SharedPreferences.Editor = sharedPreferences.edit()
                            edit.putString("username", username)
                            edit.apply()
                            val todDoIntent = Intent(applicationContext, ToDoActivity::class.java)
                            todDoIntent.putExtra("username",username)
                            startActivity(todDoIntent)

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(baseContext, "Login failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }
}
