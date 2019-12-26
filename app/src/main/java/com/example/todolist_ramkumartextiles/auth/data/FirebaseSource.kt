package com.example.todolist_ramkumartextiles.auth.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist_ramkumartextiles.model.UsersInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.Completable
import javax.inject.Inject

class FirebaseSource {

    @Inject
    constructor()

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val database:FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    fun register(email:String,password: String): LiveData<Status> {
        var status: MutableLiveData<Status> = MutableLiveData()

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            if(it.isSuccessful) {
                status.value = Status(true,"Successfully Signed Up!")
            }else{
                status.value = Status(false,"Authentication failed")
            }
        }.addOnFailureListener{
            status.value = Status(false,"Authentication failed")
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
        var id = email.substring(0,index);
        ref.child(id).setValue(username)
        database.getReference("Users").child(username).child("count").setValue(0)
    }

    fun login(email:String,password: String): LiveData<Status> {
        var status: MutableLiveData<Status> = MutableLiveData()
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
            if(it.isSuccessful) {
                status.value = Status(true,"Successfully Logged in!")
            }else{
                status.value = Status(false,"LogIn Failed")
            }
        }.addOnFailureListener{
            status.value = Status(false,"LogIn Failed")
        }
        return status
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

}