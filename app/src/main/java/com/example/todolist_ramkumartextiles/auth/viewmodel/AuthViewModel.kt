package com.example.todolist_ramkumartextiles.auth.viewmodel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist_ramkumartextiles.auth.views.LoginActivity
import com.example.todolist_ramkumartextiles.auth.views.RegisterActivity
import com.example.todolist_ramkumartextiles.auth.data.AuthRepository
import com.example.todolist_ramkumartextiles.auth.data.Status
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AuthViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    var username:String? = ""
    var email:String?= ""
    var p1:String?=""
    var p2:String?=""

    val user by lazy {
        authRepository.currentuser()
    }

    fun login(username:String,email:String,password:String):LiveData<Status>{
        var status: MutableLiveData<Status> = MutableLiveData()

        if(username.isBlank()|| email.isBlank()|| password.isBlank()){
            status.value = Status(false,"Please enter all details!")
            return status
        }
        Log.d("test","Auth"+authRepository.userPresent(username))
        if(!authRepository.userPresent(username)){
            status.value = Status(false,"Invalid Username")
            return status
        }

        return authRepository.login(email,password)
    }

    fun signup(username:String,email:String,p1:String,p2:String):LiveData<Status>{
       var status: MutableLiveData<Status> = MutableLiveData()

       if(username.isBlank()|| email.isBlank()|| p1.isBlank()|| p2.isBlank()){
            status.value = Status(false,"Please enter all details!")
            return status
       }

       if(!p1.equals(p2)) {
           status.value = Status(false,"Passwords do not match!")
           return status
       }

       return authRepository.register(email, p1)
    }

    fun storeUser(username:String, email: String,password: String,context:Context){
        authRepository.storeUser(username,email,password)
        var sharedPreferences = context.getSharedPreferences("LoginPref", Context.MODE_PRIVATE)
        val edit: SharedPreferences.Editor = sharedPreferences.edit()
        edit.putString("username", username)
        edit.putBoolean("LoginStatus",true)
        edit.apply()
    }

    fun goToLogin(view: View){
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToRegister(view: View){
        Intent(view.context, RegisterActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

}