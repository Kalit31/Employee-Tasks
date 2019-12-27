package com.example.todolist_ramkumartextiles.di.task

import com.example.todolist_ramkumartextiles.tasks.viewmodel.TaskCompViewModelFactory
import com.example.todolist_ramkumartextiles.tasks.viewmodel.TaskToDoViewModelFactory
import dagger.Component
import dagger.Subcomponent


@Subcomponent(modules = [TaskModule::class])
interface TaskComponent{

    fun inject(taskCompViewModelFactory: TaskCompViewModelFactory)

    fun inject(taskToDoViewModelFactory: TaskToDoViewModelFactory)

}