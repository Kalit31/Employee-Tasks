package com.example.todolist_ramkumartextiles.auth.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.tasks.views.activity.EmployeeActivity
import com.example.todolist_ramkumartextiles.auth.viewmodel.AuthViewModel
import com.example.todolist_ramkumartextiles.auth.viewmodel.AuthViewModelFactory
import com.example.todolist_ramkumartextiles.databinding.ActivityRegisterBinding
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
