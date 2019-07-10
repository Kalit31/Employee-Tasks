package com.example.todolist_ramkumartextiles.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.models.UsersInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        register.setOnClickListener {
            val username = etUsernameR.text.toString()
            val email = emailR.text.toString()
            val password = etPasswordR.text.toString()
            val password2 = etPasswordRR.text.toString()
            var k = 0

            for(c in password)
            {
                if(!(c.isLetter()))
                {
                    k=1
                    break
                }
            }

            if ((TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2) || TextUtils.isEmpty(username)))
            {
                Toast.makeText(applicationContext, "Please enter all details", Toast.LENGTH_SHORT).show()
            } else {
                if (k == 1) {
                    if (password == password2) {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(
                                        baseContext, "Authentication success",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //val userId = databaseReference.push().key
                                    val userInfo: UsersInformation =
                                        UsersInformation(
                                            username,
                                            email,
                                            password
                                        )
                                    databaseReference.child(username).setValue(userInfo)
                                    var ref = FirebaseDatabase.getInstance().getReference("Usernames")
                                    val id = ref.push().key
                                    ref.child(id!!).setValue(username)
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(
                                        baseContext, "Authentication failed.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(applicationContext, "Password Fields do not match", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Toast.makeText(applicationContext,"Password must contain a digit",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
