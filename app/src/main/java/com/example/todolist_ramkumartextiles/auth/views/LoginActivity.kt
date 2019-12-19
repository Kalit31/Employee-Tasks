package com.example.todolist_ramkumartextiles.auth.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.activity.EmployeeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var username = null
    private lateinit var sharedPreferences:SharedPreferences
    private var userList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            finish()
            val todDoIntent = Intent(applicationContext, EmployeeActivity::class.java)
            val sharedPreferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)
            todDoIntent.putExtra("username", sharedPreferences.getString("username", ""))
            startActivity(todDoIntent)
        }



        login.setOnClickListener {
            var username = etUsernameL.text.toString()
            var email = emailL.text.toString()
            var password = etPasswordL.text.toString()
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty((username))) {
                Toast.makeText(applicationContext, "Please enter all details", Toast.LENGTH_SHORT).show()
            } else {
                readUsers(object:
                    FirebaseCallback
                {
                    override fun onCallback(list: ArrayList<String>) {
                        if(list.contains(username))
                        {
                            val ref = FirebaseDatabase.getInstance().getReference("Users")
                                .child(username).child("email")
                            ref.addValueEventListener(object: ValueEventListener
                            {
                                override fun onCancelled(p0: DatabaseError) {
                                        Toast.makeText(applicationContext, p0.toString(),Toast.LENGTH_SHORT).show()
                                }

                                override fun onDataChange(p0: DataSnapshot) {
                                    val test_email = p0.getValue(String::class.java)
                                    if(test_email!!.compareTo(email) == 0)
                                    {
                                            auth.signInWithEmailAndPassword(email,password).
                                                    addOnCompleteListener(this@LoginActivity) {task ->
                                if (task.isSuccessful) {

                                   finish()

                                    val token = FirebaseInstanceId.getInstance().getToken()
                                    val ref = FirebaseDatabase.getInstance().getReference("Users")
                                        .child(username).child("token")
                                    ref.setValue(token)

                            val edit: SharedPreferences.Editor = sharedPreferences.edit()
                            edit.putString("username", username)
                            edit.putBoolean("LoginStatus", true)
                            edit.apply()
                            val todDoIntent = Intent(applicationContext, EmployeeActivity::class.java)
                            todDoIntent.putExtra("username", username)
                            startActivity(todDoIntent)

                        } else {
                            Toast.makeText(
                                baseContext, "Login failed.",
                                Toast.LENGTH_SHORT
                               ).show()
                           }
                         }
                     }
                                    else
                                        Toast.makeText(
                                            baseContext, "Invalid email",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                }
                            })
                        }
                        else {
                            Toast.makeText(
                                baseContext, "Invalid username",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }
        }
    }

    private fun readUsers(firebaseCallback: FirebaseCallback) {
        var ref = FirebaseDatabase.getInstance().getReference("Usernames")
        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList.clear()
                for (ds in dataSnapshot.children) {
                    val user = ds.getValue(String::class.java)
                    if (user != null) {
                        userList.add(user)
                    }
                }
                firebaseCallback.onCallback(userList)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
    private interface FirebaseCallback{
        fun onCallback(list:ArrayList<String>)
    }
}