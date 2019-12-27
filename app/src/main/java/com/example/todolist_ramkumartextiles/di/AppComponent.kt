package com.example.todolist_ramkumartextiles.di

import com.example.todolist_ramkumartextiles.di.auth.AuthComponent
import com.example.todolist_ramkumartextiles.di.auth.AuthModule
import com.example.todolist_ramkumartextiles.di.owner.OwnerComponent
import com.example.todolist_ramkumartextiles.di.owner.OwnerModule
import com.example.todolist_ramkumartextiles.di.task.TaskComponent
import com.example.todolist_ramkumartextiles.di.task.TaskModule
//import com.example.todolist_ramkumartextiles.di.tasks.TaskModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent{

    fun newAuthComponent(authModule: AuthModule):AuthComponent
    fun newTaskComponent(taskModule: TaskModule):TaskComponent
    fun newOwnerComponent(ownerModule: OwnerModule):OwnerComponent

}