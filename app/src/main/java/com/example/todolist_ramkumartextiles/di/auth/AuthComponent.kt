package com.example.todolist_ramkumartextiles.di.auth

import com.example.todolist_ramkumartextiles.auth.viewmodel.AuthViewModelFactory
import dagger.Component
import dagger.Subcomponent

@Subcomponent(modules = [AuthModule::class])
interface AuthComponent{
    fun inject(factory:AuthViewModelFactory)
}