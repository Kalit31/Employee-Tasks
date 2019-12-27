package com.example.todolist_ramkumartextiles.owners.views.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.owners.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_owners_login.*

class ownersLogIn : AppCompatActivity() {

    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owners_login)

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        var sharedPreferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("KEY_OWNERS_LOGIN",false))        {
            finish()
            startActivity(Intent(applicationContext, OwnersAct::class.java))
        }

        logino.setOnClickListener {
                loginViewModel.login(etUsernameO.text.toString(),etPasswordO.text.toString()).observe(this,
                    Observer {
                        Toast.makeText(applicationContext, it.message,
                            Toast.LENGTH_SHORT).show()
                        if(it.complete){
                            sharedPreferences.edit().putBoolean("KEY_OWNERS_LOGIN",true).apply()
                            startActivity(Intent(applicationContext, OwnersAct::class.java))
                            finish()
                        }
                    }
                )
        }
    }
}
