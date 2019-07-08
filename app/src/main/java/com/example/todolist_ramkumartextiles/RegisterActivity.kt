package com.example.todolist_ramkumartextiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        register.setOnClickListener {
            val email = etUsernameR.text.toString()
            val password = etPasswordR.text.toString()
            val password2 = etPasswordRR.text.toString()

            if ((TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2))) {
                Toast.makeText(applicationContext, "Please enter all details", Toast.LENGTH_SHORT).show()
            } else {
                if (password == password2) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(
                                    baseContext, "Authentication success",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(
                                    baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
                else{
                    Toast.makeText(applicationContext,"Password Fields do not match",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
