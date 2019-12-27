package com.example.todolist_ramkumartextiles.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist_ramkumartextiles.EmployeeApp
import com.example.todolist_ramkumartextiles.di.task.TaskModule
import com.example.todolist_ramkumartextiles.tasks.data.repo.TaskRepository
import javax.inject.Inject

class TaskCompViewModelFactory(): ViewModelProvider.NewInstanceFactory() {

    @Inject
    lateinit var taskRepository: TaskRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        EmployeeApp.appComponent.newTaskComponent(TaskModule()).inject(this)
        return TaskCompletedViewModel(taskRepository) as T
    }
}