package com.example.todolist_ramkumartextiles.auth.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.tasks.views.activity.EmployeeActivity
import com.example.todolist_ramkumartextiles.auth.viewmodel.AuthViewModel
import com.example.todolist_ramkumartextiles.auth.viewmodel.AuthViewModelFactory
import com.example.todolist_ramkumartextiles.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        authViewModel = ViewModelProviders.of(this, AuthViewModelFactory()).get(AuthViewModel::class.java)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            finish()
            val todDoIntent = Intent(applicationContext, EmployeeActivity::class.java)
            startActivity(todDoIntent)
        }

        login.setOnClickListener {
            authViewModel.login(emailL.text.toString(),etPasswordL.text.toString())
                .observe(this, Observer{
                    Toast.makeText(applicationContext,it.message,Toast.LENGTH_SHORT).show()
                    if(it.complete){
                        finish()
                        authViewModel.updateUsername(emailL.text.toString())
                        startActivity(Intent(this, EmployeeActivity::class.java))
                    }
                })
        }

        signUpUser.setOnClickListener{
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }
}