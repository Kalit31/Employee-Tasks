package com.example.todolist_ramkumartextiles.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist_ramkumartextiles.EmployeeApp
import com.example.todolist_ramkumartextiles.auth.data.repo.AuthRepository
import com.example.todolist_ramkumartextiles.di.auth.AuthModule
import javax.inject.Inject

class AuthViewModelFactory ():
    ViewModelProvider.NewInstanceFactory() {

      @Inject
      lateinit var authRepository: AuthRepository


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        EmployeeApp.appComponent.newAuthComponent(AuthModule()).inject(this)
        return AuthViewModel(authRepository) as T
    }
}