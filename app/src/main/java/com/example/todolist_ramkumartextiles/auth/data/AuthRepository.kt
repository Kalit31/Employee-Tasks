package com.example.todolist_ramkumartextiles.auth.data

import android.util.Log
import androidx.lifecycle.LiveData

class AuthRepository (private val firebaseSource: FirebaseSource){
    fun login(email:String,password:String): LiveData<Status> = firebaseSource.login(email,password)
    fun register(email: String,password: String): LiveData<Status> = firebaseSource.register(email,password)
    fun logout() =firebaseSource.logout()
    fun currentuser() = firebaseSource.currentUser()
    fun storeUser(username:String, email: String,password: String) = firebaseSource.storeUser(username,email,password)
//    fun userPresent(username: String):LiveData<Boolean> = firebaseSource.userPresent(username)
}