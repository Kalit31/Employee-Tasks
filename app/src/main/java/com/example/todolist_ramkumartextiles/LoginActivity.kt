package com.example.todolist_ramkumartextiles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null)
        {
            finish()
            val todDoIntent = Intent(applicationContext, ToDoActivity::class.java)
            startActivity(todDoIntent)
        }

        login.setOnClickListener {
            val email = etUsernameL.text.toString()
            val password = etPasswordL.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            {
                Toast.makeText(applicationContext,"Please enter all details", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            finish()
                            val todDoIntent = Intent(applicationContext, ToDoActivity::class.java)
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
