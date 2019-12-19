package com.example.todolist_ramkumartextiles.auth.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist_ramkumartextiles.model.UsersInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.Completable
import javax.inject.Inject

class FirebaseSource {

    @Inject
    constructor()

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val databaseReference:DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("Users")
    }

    fun register(email:String,password: String): LiveData<RegisterStatus> {
        var status: MutableLiveData<RegisterStatus> = MutableLiveData()

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            if(it.isSuccessful) {
                status.value = RegisterStatus(true,"Successfully Signed Up!")
            }else{
                status.value = RegisterStatus(false,"Authentication failed")
            }
        }.addOnFailureListener{
            status.value = RegisterStatus(false,"Authentication failed")
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
        databaseReference.child(username).setValue(userInfo)
        val ref = FirebaseDatabase.getInstance().getReference("Usernames")
        var id = ref.push().key
        ref.child(id!!).setValue(username)
        databaseReference.child(username).child("count").setValue(0)
    }

    fun login(email:String,password: String) = Completable.create { emitter ->  
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
            if(!emitter.isDisposed){
                if(it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser


}