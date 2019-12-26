package com.example.todolist_ramkumartextiles.auth.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist_ramkumartextiles.auth.data.Status
import com.example.todolist_ramkumartextiles.auth.data.prefs.AuthPreferenceHelper
import com.example.todolist_ramkumartextiles.model.UsersInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId


class AuthRepository(val prefs:AuthPreferenceHelper){

    companion object {
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    }

    fun register(username:String,email:String,password: String): LiveData<Status> {
        var status: MutableLiveData<Status> = MutableLiveData()

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            if(it.isSuccessful) {
                status.value = Status(
                    true,
                    "Successfully Signed Up!"
                )
                storeUser(username,email,password)
            }else{
                status.value = Status(
                    false,
                    "Authentication failed"
                )
            }
        }.addOnFailureListener{
            status.value = Status(
                false,
                "Authentication failed"
            )
        }
        return status
    }


    fun login(email:String,password: String): LiveData<Status> {
        var status: MutableLiveData<Status> = MutableLiveData()
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
            if(it.isSuccessful) {
                status.value = Status(
                    true,
                    "Successfully Logged in!"
                )
            }else{
                status.value =
                    Status(false, "LogIn Failed")
            }
        }.addOnFailureListener{
            status.value =
                Status(false, "LogIn Failed")
        }
        return status
    }


    fun storeUser(username:String, email: String,password: String){
        val token = FirebaseInstanceId.getInstance().token
        val userInfo: UsersInformation =
            UsersInformation(
                username,
                email,
                password,
                token
            )
        database.getReference("Users").child(username).setValue(userInfo)
        val ref = FirebaseDatabase.getInstance().getReference("Usernames")
        var index = email.indexOf('@')
        var key = email.substring(0,index)
            ref.child(key).setValue(username)
        database.getReference("Users").child(username).child("count").setValue(0)
        setUsername(username)
        setLoginStatus(true)
    }

    fun setUsername(username: String){
        prefs.setUsername(username)
    }

    fun setLoginStatus(status:Boolean){
        prefs.setLoginStatus(status)
    }

    fun logout() = firebaseAuth.signOut()
}