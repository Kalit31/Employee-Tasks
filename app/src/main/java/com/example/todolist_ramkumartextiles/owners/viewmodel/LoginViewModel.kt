package com.example.todolist_ramkumartextiles.owners.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist_ramkumartextiles.owners.data.Status

class LoginViewModel(): ViewModel(){

    var status: MutableLiveData<Status> = MutableLiveData()
     fun login(username:String,password:String):LiveData<Status>{
        if(username.isBlank() || password.isBlank()){
            status.value = Status(false,"Please enter all details")
            return status
        }
        if(username.trim().equals("test") && password.trim().equals("test@123")) {
            status.value = Status(true,"Login successful")
        }else{
            status.value = Status(false,"Incorrect credentials")
        }
         return status
     }
}