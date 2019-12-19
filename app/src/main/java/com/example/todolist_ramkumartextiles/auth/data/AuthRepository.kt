package com.example.todolist_ramkumartextiles.auth.data

import androidx.lifecycle.LiveData

class AuthRepository (private val firebaseSource: FirebaseSource){
    fun login(email:String,password:String)= firebaseSource.login(email,password)
    fun register(email: String,password: String): LiveData<RegisterStatus> = firebaseSource.register(email,password)
    fun logout() =firebaseSource.logout()
    fun currentuser() = firebaseSource.currentUser()
    fun storeUser(username:String, email: String,password: String) = firebaseSource.storeUser(username,email,password)
}