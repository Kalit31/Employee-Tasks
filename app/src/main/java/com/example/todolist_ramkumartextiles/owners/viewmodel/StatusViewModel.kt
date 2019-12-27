package com.example.todolist_ramkumartextiles.owners.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist_ramkumartextiles.owners.data.repo.OwnersRepository

class StatusViewModel(private val ownersRepository: OwnersRepository): ViewModel(){

    var userData: MutableLiveData<ArrayList<String>> = MutableLiveData()

    init{
        loadUsers()
    }

    fun getUsers(): LiveData<ArrayList<String>> {
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

}