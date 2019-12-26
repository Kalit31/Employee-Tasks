package com.example.todolist_ramkumartextiles.di

import com.example.todolist_ramkumartextiles.di.auth.AuthComponent
import com.example.todolist_ramkumartextiles.di.auth.AuthModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent{

    fun newAuthComponent(c:AuthModule):AuthComponent
}