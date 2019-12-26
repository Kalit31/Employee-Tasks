package com.example.todolist_ramkumartextiles.auth.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.activity.EmployeeActivity
import com.example.todolist_ramkumartextiles.auth.viewmodel.AuthViewModel
import com.example.todolist_ramkumartextiles.auth.viewmodel.AuthViewModelFactory
import com.example.todolist_ramkumartextiles.databinding.ActivityRegisterBinding
import com.example.todolist_ramkumartextiles.model.UsersInformation
import com.google.android.gms.common.api.internal.LifecycleActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
   private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register)
        authViewModel = ViewModelProviders.of(this,AuthViewModelFactory()).get(AuthViewModel::class.java)

        var owner: LifecycleOwner = this

        register.setOnClickListener{
                authViewModel.register(etUsernameR.text.toString(),
                    emailR.text.toString(),etPasswordR.text.toString(),
                    etPasswordRR.text.toString()).observe(owner, Observer {
                    Toast.makeText(applicationContext,it.message,Toast.LENGTH_SHORT).show()
                    if(it.complete){
                        finish()
                        startActivity(Intent(this, EmployeeActivity::class.java))
                    }
                })
        }


    }
}
