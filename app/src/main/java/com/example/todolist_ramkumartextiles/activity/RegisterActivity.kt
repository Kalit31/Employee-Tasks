package com.example.todolist_ramkumartextiles.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.models.EmployeeStatus
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
                                    Toast.makeText(
                                        baseContext, "Authentication success",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val userInfo: UsersInformation =
                                        UsersInformation(
                                            username,
                                            email,
                                            password
                                        )
                                    databaseReference.child(username).setValue(userInfo)
                                    val ref = FirebaseDatabase.getInstance().getReference("Usernames")
                                    var id = ref.push().key
                                    ref.child(id!!).setValue(username)
                                    databaseReference.child(username).child("count").setValue(0)
                                    var sharedPreferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)
                                    val edit: SharedPreferences.Editor = sharedPreferences.edit()
                                    edit.putString("username", username)
                                    edit.putBoolean("LoginStatus",true)
                                    edit.apply()
                                } else {
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
