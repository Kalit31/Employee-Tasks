package com.example.todolist_ramkumartextiles.owners.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist_ramkumartextiles.EmployeeApp
import com.example.todolist_ramkumartextiles.di.owner.OwnerModule
import com.example.todolist_ramkumartextiles.owners.data.repo.OwnersRepository
import javax.inject.Inject

class StatusViewModelFactory (): ViewModelProvider.NewInstanceFactory(){
    @Inject
    lateinit var ownersRepository: OwnersRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        EmployeeApp.appComponent.newOwnerComponent(OwnerModule()).inject(this)
        return StatusViewModel(ownersRepository) as T
    }
}