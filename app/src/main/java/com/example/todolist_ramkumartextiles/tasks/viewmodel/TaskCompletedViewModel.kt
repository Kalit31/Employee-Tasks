package com.example.todolist_ramkumartextiles.tasks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist_ramkumartextiles.tasks.data.repo.TaskRepository
import com.example.todolist_ramkumartextiles.tasks.data.room.dataclasses.TaskInformation

class TaskCompletedViewModel(private val taskRepository: TaskRepository): ViewModel(){
    var taskData : MutableLiveData<ArrayList<TaskInformation>> = MutableLiveData()

    init{
        fetchTasks()
    }

    fun getTasks(): LiveData<ArrayList<TaskInformation>> {
        return taskData
    }

    fun fetchTasks(){
        taskRepository.loadCompletedTasks(object : TaskRepository.TaskCallback{
            override fun onCallback(tasks: ArrayList<TaskInformation>) {
                taskData.value= tasks
            }
        })
    }

}