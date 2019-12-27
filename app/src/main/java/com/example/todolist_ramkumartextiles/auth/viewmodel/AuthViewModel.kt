package com.example.todolist_ramkumartextiles.auth.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist_ramkumartextiles.auth.data.repo.AuthRepository
import com.example.todolist_ramkumartextiles.auth.data.Status
import com.example.todolist_ramkumartextiles.auth.data.repo.AuthRepository.Companion.database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class AuthViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    var username:String? = ""
    var email:String?= ""
    var p1:String?=""
    var p2:String?=""


    fun login(email:String,password:String):LiveData<Status>{
        var status: MutableLiveData<Status> = MutableLiveData()

        if(email.isBlank()|| password.isBlank()){
            status.value = Status(false,"Please enter all details!")
            return status
        }
        return authRepository.login(email,password)
    }


    fun register(username:String,email:String,p1:String,p2:String):LiveData<Status>{
       var status: MutableLiveData<Status> = MutableLiveData()

       if(username.isBlank()|| email.isBlank()|| p1.isBlank()|| p2.isBlank()){
            status.value = Status(false,"Please enter all details!")
            return status
       }
       if(!p1.equals(p2)) {
           status.value = Status(false,"Passwords do not match!")
           return status
       }
       return authRepository.register(username, email, p1)
    }


    fun updateUsername(email:String){
        var index = email.indexOf('@')
        var key = email.substring(0,index)
        database.getReference("Usernames").child(key).addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                //DO NOTHING
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var username =  dataSnapshot.getValue(String::class.java).toString()
                authRepository.setUsername(username)
                authRepository.setLoginStatus(true)
            }
        })
    }
}