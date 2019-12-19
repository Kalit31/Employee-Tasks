package com.example.todolist_ramkumartextiles.auth.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.todolist_ramkumartextiles.auth.views.LoginActivity
import com.example.todolist_ramkumartextiles.auth.views.RegisterActivity
import com.example.todolist_ramkumartextiles.auth.data.AuthRepository
import com.example.todolist_ramkumartextiles.auth.data.RegisterStatus
import com.example.todolist_ramkumartextiles.auth.views.SignUpState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class AuthViewModel(
    private val authRepository: AuthRepository
): ViewModel() {


    var username:String? = ""
    var email:String?= ""
    var p1:String?=""
    var p2:String?=""

    val authListener: AuthListener? = null

    val user by lazy {
        authRepository.currentuser()
    }

    fun login(){


    }

    fun signup(username:String,email:String,p1:String,p2:String):LiveData<RegisterStatus>{
       var status: MutableLiveData<RegisterStatus> = MutableLiveData()

       if(username.isBlank()|| email.isBlank()|| p1.isBlank()|| p2.isBlank()){
            status.value = RegisterStatus(false,"Please enter all details!")
            return status
       }

       if(!p1.equals(p2)) {
           status.value = RegisterStatus(false,"Passwords do not match!")
           return status
       }

       return authRepository.register(email, p1)
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

    fun storeUser(username:String, email: String,password: String){
        authRepository.storeUser(username,email,password)
    }
}