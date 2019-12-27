package com.example.todolist_ramkumartextiles.owners.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist_ramkumartextiles.owners.data.Status
import com.example.todolist_ramkumartextiles.owners.data.repo.OwnersRepository

class AssignTaskViewModel(
    private val ownersRepository: OwnersRepository): ViewModel(){
    var userData: MutableLiveData<ArrayList<String>> = MutableLiveData()

    init{
        loadUsers()
    }

    fun getUsers(): LiveData<ArrayList<String>>{
        userData.value?.add(0,"Select Employee")
        return userData
    }

    fun loadUsers(){
        ownersRepository.loadUsers(object:OwnersRepository.UserCallback{
            override fun onCallback(users: ArrayList<String>) {
                userData.value = users
            }
        })
    }

    fun submitTask(employeeName:String,taskDesc:String,dateString:String):LiveData<Status>{
        var status: MutableLiveData<Status> = MutableLiveData()

        if(employeeName.isBlank()||taskDesc.isBlank()||dateString.isBlank())
            status.value = Status(false, "Incomplete Task")
        else if(employeeName.equals("Select Employee"))
            status.value = Status(false, "Please select an employee")
        else{
            ownersRepository.uploadTask(employeeName,taskDesc,dateString)
            status.value = Status(true,"Uploaded Task")
        }
        return status
    }

    fun removeNotifications(){
        ownersRepository.removeNotifications()
    }
}